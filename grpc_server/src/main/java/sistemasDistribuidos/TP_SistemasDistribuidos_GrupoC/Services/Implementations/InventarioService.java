package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
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

    ///Obtener los inventarios activos:
    @Override
    public List<InventarioDTO> inventariosActivos() {
        return inventarioRepository.findByEliminadoFalse()
                .stream()
                .map(InventarioMapper::aDTO)
                .collect(Collectors.toList());
    }

    ///Obtener un inventario por categoria y descripcion:
    @Override
    public Optional<Inventario> inventarioPorCategoriaYDescripcion(Categoria categoria, String descripcion) {
        return inventarioRepository.findByCategoriaAndDescripcion(categoria, descripcion);
    }

    ///Agregar un inventario:
    @Override
    public InventarioDTO crear(CrearInventarioDTO dto) {
        if (dto.getCategoria() == null || dto.getCategoria() == Categoria.DESCONOCIDA) {
            throw new IllegalArgumentException("La categoría es obligatoria.");
        }

        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }

        if (dto.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }

        Usuario usuarioActual = usuarioService.getUsuarioLogueado();
        LocalDateTime ahora = LocalDateTime.now();

        //Buscar inventario existente:
        Optional<Inventario> existenteOpt = inventarioPorCategoriaYDescripcion(dto.getCategoria(), dto.getDescripcion());

        Inventario inventarioFinal;

        if (existenteOpt.isPresent()) {
            //Ya existe: sumamos la cantidad, actualizamos modificación y usuario.
            inventarioFinal = existenteOpt.get();
            inventarioFinal.setCantidad(inventarioFinal.getCantidad() + dto.getCantidad());
            inventarioFinal.setFechaHoraModificacion(ahora);
            inventarioFinal.setUsuarioModificacion(usuarioActual);

        } else {
            //No existe: creamos nuevo registro.
            inventarioFinal = InventarioMapper.aEntidad(dto);
            inventarioFinal.setEliminado(false);
            inventarioFinal.setFechaHoraAlta(ahora);
            inventarioFinal.setFechaHoraModificacion(ahora);
            inventarioFinal.setUsuarioAlta(usuarioActual);
            inventarioFinal.setUsuarioModificacion(usuarioActual);
        }

        inventarioFinal = inventarioRepository.save(inventarioFinal);
        return InventarioMapper.aDTO(inventarioFinal);
    }

    ///Modificar un inventario:
    @Override
    @Transactional
    public InventarioDTO modificar(ModificarInventarioDTO dto) {
        if (dto.getDescripcion() == null || dto.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("La descripción es obligatoria.");
        }

        if (dto.getCantidad() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }

        //Buscar el inventario:
        Inventario inventario = inventarioRepository.findById(dto.getIdInventario())
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado"));

        //Si cambió la descripción, verificar duplicados:
        if (!inventario.getDescripcion().equals(dto.getDescripcion())) {
            Optional<Inventario> duplicadoOpt = inventarioRepository
                    .findByCategoriaAndDescripcion(inventario.getCategoria(), dto.getDescripcion());

            if (duplicadoOpt.isPresent() && !duplicadoOpt.get().getIdInventario().equals(inventario.getIdInventario())) {
                throw new IllegalArgumentException(
                        "Ya existe un inventario con esta categoría y descripción"
                );
            }
        }

        //Actualizar campos:
        inventario.setDescripcion(dto.getDescripcion());
        inventario.setCantidad(dto.getCantidad());
        inventario.setFechaHoraModificacion(LocalDateTime.now());
        inventario.setUsuarioModificacion(usuarioService.getUsuarioLogueado());

        return InventarioMapper.aDTO(inventarioRepository.save(inventario));
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

    ///Habilitar un inventario:
    @Override
    @Transactional
    public boolean habilitarInventario(Long idInventario) {
        Inventario inventario = inventarioRepository.findById(idInventario)
                .orElseThrow(() -> new EntityNotFoundException("Inventario no encontrado con id: " + idInventario));

        if (!inventario.isEliminado()) {
            throw new IllegalStateException("El inventario ya está habilitado");
        }

        inventario.setEliminado(false);
        inventario.setFechaHoraModificacion(LocalDateTime.now());
        inventario.setUsuarioModificacion(usuarioService.getUsuarioLogueado());

        inventarioRepository.save(inventario);
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