package org.empuje_comunitario.graphql_service.service.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.empuje_comunitario.graphql_service.dto.FiltroDonacionDTO;
import org.empuje_comunitario.graphql_service.graphql.mapper.EstadoEliminadoMapper;
import org.empuje_comunitario.graphql_service.graphql.mapper.FiltroDonacionMapperGraphQL;
import org.empuje_comunitario.graphql_service.graphql.mapper.LocalDateTimeMapper;
import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionGraphQL;
import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionInputGraphQL;
import org.empuje_comunitario.graphql_service.mapper.FiltroDonacionMapper;
import org.empuje_comunitario.graphql_service.model.FiltroDonacion;
import org.empuje_comunitario.graphql_service.model.Usuario;
import org.empuje_comunitario.graphql_service.repository.IFiltroDonacionRepository;
import org.empuje_comunitario.graphql_service.repository.IUsuarioRepository;
import org.empuje_comunitario.graphql_service.service.interfaces.IFiltroDonacionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FiltroDonacionService implements IFiltroDonacionService {
	///Atributos:
	private final IFiltroDonacionRepository filtroDonacionRepository;
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
	
	///Listar todos los filtros guardados por un usuario:
    @Override
    @Transactional(readOnly = true)
	public List<FiltroDonacionGraphQL> filtrosUsuarioLogueado() {
    	//Obtenemos el usuario logueado:
    	Usuario usuario = obtenerUsuarioLogueado();

    	//Traemos los filtros de ese usuario:
        List<FiltroDonacion> filtros = filtroDonacionRepository.findByUsuario(usuario);
        
        //Si el usuario no tiene filtros:
        if (filtros == null) {
            filtros = new ArrayList<>(); //Generamos una lista vacía para que no sea null.
        }
        
        //Mapear a DTO:
        List<FiltroDonacionDTO> filtrosDTO =  filtros.stream()
								                .map(FiltroDonacionMapper::aDTO)
								                .collect(Collectors.toList());
        
        //Mapeamos a GraphQL:
        return filtrosDTO.stream()
        		.map(FiltroDonacionMapperGraphQL::aGraphQL)
        		.collect(Collectors.toList());
    }

    ///Buscar un filtro por nombre e id de usuario:
    @Override
    public Optional<FiltroDonacion> filtroPorNombreYUsuario(String nombreFiltroDonacion, Usuario usuario) {
    	return filtroDonacionRepository.findByNombreFiltroAndUsuario(nombreFiltroDonacion, usuario);
    }
    
    ///Crear filtro:
    @Override
    @Transactional
    public FiltroDonacionGraphQL crearFiltro(FiltroDonacionInputGraphQL input) {
    	//Buscamos el usuario logueado:
    	Usuario usuarioEntidad = obtenerUsuarioLogueado();
    	
    	//Si existe el usuario, verificamos que el filtro no exista previamente:
    	Optional<FiltroDonacion> filtroOpt = filtroPorNombreYUsuario(input.getNombreFiltro(), usuarioEntidad);
    	
    	//Si existe el filtro...
    	if (filtroOpt.isPresent()) {
    		throw new EntityExistsException("El filtro de donación ya existe");
    	}
    	
    	//Si el usuario existe y el filtro no, podemos crear el filtro:
    	FiltroDonacionDTO filtroDTO = FiltroDonacionMapperGraphQL.aDTO(input); //Mapeamos el GraphQL a DTO.
    	FiltroDonacion filtroEntidad = FiltroDonacionMapper.aEntidad(filtroDTO); //Mapeamos la entidad a partir del DTO.
    	
    	filtroEntidad.setUsuario(usuarioEntidad); //Cargamos el usuario de la base de datos.
    	
    	FiltroDonacion filtroGuardado = filtroDonacionRepository.save(filtroEntidad); //Persistimos el filtro.
    	
    	FiltroDonacionDTO filtroGuardadoDTO = FiltroDonacionMapper.aDTO(filtroGuardado); //Mapeamos a DTO el filtro persistido.
    	return FiltroDonacionMapperGraphQL.aGraphQL(filtroGuardadoDTO); //Retornamos el mapeo del DTO a GraphQL.
    }
    
    ///Modificar filtro:
    @Override
    @Transactional
    public FiltroDonacionGraphQL modificarFiltro(FiltroDonacionInputGraphQL input) {
    	//Buscar el filtro existente por ID:
        FiltroDonacion filtroEntidad = filtroDonacionRepository.findById(input.getIdFiltroDonacion())
            .orElseThrow(() -> new EntityNotFoundException("Filtro de donación no encontrado"));

        //Verificar si se cambió el nombre:
        if (!filtroEntidad.getNombreFiltro().equals(input.getNombreFiltro())) {
            //Si cambió, revisar que no exista otro filtro con ese nombre para el mismo usuario:
            Optional<FiltroDonacion> filtroDuplicado = filtroPorNombreYUsuario(input.getNombreFiltro(), filtroEntidad.getUsuario());

            if (filtroDuplicado.isPresent()) {
                throw new EntityExistsException("Ya existe un filtro con ese nombre para este usuario");
            }
        }

        //Actualizar campos del filtro:
        filtroEntidad.setNombreFiltro(input.getNombreFiltro());
        filtroEntidad.setFechaHoraAltaDesde(LocalDateTimeMapper.desdeString(input.getFechaHoraAltaDesde()));
        filtroEntidad.setFechaHoraAltaHasta(LocalDateTimeMapper.desdeString(input.getFechaHoraAltaHasta()));
        filtroEntidad.setEliminado(EstadoEliminadoMapper.aEnum(input.getEliminado()));

        //Persistir la entidad:
        filtroDonacionRepository.save(filtroEntidad);

        //Devolver GraphQL actualizado:
        FiltroDonacionDTO dto = FiltroDonacionMapper.aDTO(filtroEntidad);
        return FiltroDonacionMapperGraphQL.aGraphQL(dto);
    }
    
    ///Eliminar filtro:
    @Override
    @Transactional
    public boolean eliminarFiltro(Long idFiltroDonacion) {
    	///Buscar el filtro existente por ID:
        FiltroDonacion filtroEntidad = filtroDonacionRepository.findById(idFiltroDonacion)
            .orElseThrow(() -> new EntityNotFoundException("Filtro de donación no encontrado"));
        
        ///Eliminar el filtro:
        filtroDonacionRepository.delete(filtroEntidad);
        
        return true;
    }
}
