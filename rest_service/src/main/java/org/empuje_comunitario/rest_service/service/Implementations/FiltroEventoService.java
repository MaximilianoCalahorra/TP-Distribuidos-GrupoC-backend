package org.empuje_comunitario.rest_service.service.Implementations;

import org.empuje_comunitario.rest_service.dto.FiltroEventoDTO;
import org.empuje_comunitario.rest_service.model.FiltroEvento;
import org.empuje_comunitario.rest_service.model.Usuario;
import org.empuje_comunitario.rest_service.mapper.FiltroEventoMapper;
import org.empuje_comunitario.rest_service.repository.IFiltroEventoRepository;
import org.empuje_comunitario.rest_service.repository.IUsuarioRepository;
import org.empuje_comunitario.rest_service.service.Interfaces.IFiltroEventoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FiltroEventoService implements IFiltroEventoService { 

    private final IFiltroEventoRepository filtroEventoRepository;
    private final IUsuarioRepository usuarioRepository;

    ///Obtener usuario logueado:
	private Usuario obtenerUsuarioLogueado() {
	    //Obtenemos el usuario logueado desde el contexto de seguridad:
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();

	    //Buscamos al usuario en la base:
	    return usuarioRepository.findByNombreUsuario(username)
	            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
	}

	///Guardar filtro:
    @Override
    @Transactional
    public FiltroEventoDTO guardarFiltro(FiltroEventoDTO dto) {
    	//Verificamos que el usuario por el que se quiere filtrar exista:
    	Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(dto.getEmailUsuario());
    	
    	//Si no existe...
    	if (!usuarioOpt.isPresent()) {
    		throw new EntityNotFoundException("El usuario con email " + dto.getEmailUsuario() + " no existe.");
    	}
    	
    	//Accedemos al usuario:
    	Usuario usuarioEntidad = usuarioOpt.get();
    	
    	//Si existe el usuario, verificamos que el filtro no exista previamente:
    	Optional<FiltroEvento> filtroOpt = filtroPorNombreYUsuario(dto.getNombreFiltro(), usuarioEntidad);
    	
    	//Si existe el filtro...
    	if (filtroOpt.isPresent()) {
    		throw new EntityExistsException("El filtro de evento ya existe");
    	}
    	
    	//Si el usuario existe y el filtro no, podemos crear el filtro:
    	FiltroEvento filtroEntidad = FiltroEventoMapper.aEntidad(dto); //Mapeamos la entidad a partir del DTO.
    	
    	filtroEntidad.setUsuario(usuarioEntidad); //Cargamos el usuario de la base de datos.
    	
    	FiltroEvento filtroGuardado = filtroEventoRepository.save(filtroEntidad); //Persistimos el filtro.
    	
    	return FiltroEventoMapper.aDTO(filtroGuardado); //retornamos el dto del filtro persistido
    
    }
 
    ///Modificar filtro:
    @Override
    @Transactional
    public FiltroEventoDTO modificarFiltro(FiltroEventoDTO dto) {
    	//Buscar el filtro existente por ID:
        FiltroEvento filtroEntidad = filtroEventoRepository.findById(dto.getId())
            .orElseThrow(() -> new EntityNotFoundException("Filtro de evento no encontrado"));

        //Verificar si se cambió el nombre:
        if (!filtroEntidad.getNombreFiltro().equals(dto.getNombreFiltro())) {
            //Si cambió, revisar que no exista otro filtro con ese nombre para el mismo usuario:
            Optional<FiltroEvento> filtroDuplicado = filtroPorNombreYUsuario(dto.getNombreFiltro(), filtroEntidad.getUsuario());

            if (filtroDuplicado.isPresent()) {
                throw new EntityExistsException("Ya existe un filtro con ese nombre para este usuario");
            }
        }

        //Actualizar campos del filtro:
        filtroEntidad.setNombreFiltro(dto.getNombreFiltro());
        filtroEntidad.setEmailUsuario(dto.getEmailUsuario());
        filtroEntidad.setFechaHoraDesde(dto.getFechaHoraDesde());
        filtroEntidad.setFechaHoraHasta(dto.getFechaHoraHasta());
        filtroEntidad.setRepartoDonaciones(dto.getRepartoDonaciones());

        //Persistir la entidad:
        filtroEventoRepository.save(filtroEntidad);

        //Devolver dto actualizado:
        return FiltroEventoMapper.aDTO(filtroEntidad);
        
    }

    ///Eliminar filtro:
    @Override
    @Transactional
    public boolean eliminarFiltro(Long idFiltroEvento) {
    	///Buscar el filtro existente por ID:
        FiltroEvento filtroEntidad = filtroEventoRepository.findById(idFiltroEvento)
            .orElseThrow(() -> new EntityNotFoundException("Filtro de evento no encontrado"));
        
        ///Eliminar el filtro:
        filtroEventoRepository.delete(filtroEntidad);
        
        return true;
    }

	///Listar todos los filtros guardados por un usuario:
    @Override
    @Transactional(readOnly = true)
	public List<FiltroEventoDTO> filtrosUsuarioLogueado() {
    	//Obtenemos el usuario logueado:
    	Usuario usuario = obtenerUsuarioLogueado();

    	//Traemos los filtros de ese usuario:
        List<FiltroEvento> filtros = filtroEventoRepository.findByUsuario(usuario);
        
        //Si el usuario no tiene filtros:
        if (filtros == null) {
            filtros = new ArrayList<>(); //Generamos una lista vacía para que no sea null.
        }
        
        //Mapear a DTO:
        List<FiltroEventoDTO> filtrosDTO =  filtros.stream()
								                .map(FiltroEventoMapper::aDTO)
								                .collect(Collectors.toList());
        
        return filtrosDTO;
    }

    ///Buscar un filtro por nombre e id de usuario:
    @Override
    public Optional<FiltroEvento> filtroPorNombreYUsuario(String nombreFiltroEvento, Usuario usuario) {
    	return filtroEventoRepository.findByNombreFiltroAndUsuario(nombreFiltroEvento, usuario);
    }
}
