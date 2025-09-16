package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.InventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.InventarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IInventarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IInventarioService;

@Service("inventarioService")
@RequiredArgsConstructor
public class InventarioService implements IInventarioService {
	///Atributos:
	private final IInventarioRepository inventarioRepository;
	private final UsuarioService usuarioService;
	
	///Obtener todos los inventarios:
	@Override
	public List<InventarioDTO> inventarios() {
		return inventarioRepository.findAll()
								     .stream()
								     .map(InventarioMapper::aDTO)
								     .collect(Collectors.toList());
	}
	
	///Agregar un inventario:
	@Override
	public InventarioDTO crear(CrearInventarioDTO inventario) {
		if (inventario.getCategoria() == null  || inventario.getCategoria() == Categoria.DESCONOCIDA) {
	        throw new IllegalArgumentException("La categoría es obligatoria.");
	    }

	    if (inventario.getDescripcion() == null || inventario.getDescripcion().isBlank()) {
	        throw new IllegalArgumentException("La descripción es obligatoria.");
	    }

	    if (inventario.getCantidad() < 0) {
	        throw new IllegalArgumentException("La cantidad no puede ser negativa.");
	    }
		
		Inventario entidad = InventarioMapper.aEntidad(inventario);
		
		entidad.setEliminado(false);
		entidad.setFechaHoraAlta(LocalDateTime.now());
		entidad.setFechaHoraModificacion(LocalDateTime.now());
		entidad.setUsuarioAlta(usuarioService.getUsuarioLogueado());
		entidad.setUsuarioModificacion(usuarioService.getUsuarioLogueado());
		
		return InventarioMapper.aDTO(inventarioRepository.save(entidad));
	}
	
	///Modificar un inventario:
	@Override
	@Transactional
	public InventarioDTO modificar(ModificarInventarioDTO inventario) {
	    if (inventario.getDescripcion() == null || inventario.getDescripcion().isBlank()) {
	        throw new IllegalArgumentException("La descripción es obligatoria.");
	    }

	    if (inventario.getCantidad() < 0) {
	        throw new IllegalArgumentException("La cantidad no puede ser negativa.");
	    }

	    Inventario entidad = inventarioRepository.findById(inventario.getIdInventario())
	            .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

	    entidad.setDescripcion(inventario.getDescripcion());
	    entidad.setCantidad(inventario.getCantidad());
	    entidad.setFechaHoraModificacion(LocalDateTime.now());
	   // entidad.setUsuarioModificacion(usuarioService.getUsuarioLogueado());

	    return InventarioMapper.aDTO(inventarioRepository.save(entidad));
	}
	
	///Eliminar lógicamente un inventario:
	@Override
	@Transactional
	public boolean eliminarLogico(Long idInventario) {
	    Inventario entidad = inventarioRepository.findById(idInventario)
	            .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

	    entidad.setEliminado(true);
	    entidad.setFechaHoraModificacion(LocalDateTime.now());
	    entidad.setUsuarioModificacion(usuarioService.getUsuarioLogueado());

	    inventarioRepository.save(entidad);
	    return true;
	}

    /// Obtengo un inventario por ID
    @Override
    public Inventario obtenerInventarioPorId(Long idInventario) {
        return inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + idInventario));
    }
    /// Actualizo el registro existente en la base de datos
    @Override
    public void actualizarInventario(Inventario inventario) {
        // guardo la entidad inventario (ya viene seteado)
        inventarioRepository.save(inventario);
    }
}