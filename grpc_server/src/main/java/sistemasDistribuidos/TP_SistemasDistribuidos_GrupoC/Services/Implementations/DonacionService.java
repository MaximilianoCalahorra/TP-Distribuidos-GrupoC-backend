package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.DonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IDonacionRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IUsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IEventoSolidarioService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IInventarioService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IDonacionService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@PreAuthorize("hasRole('PRESIDENTE') or hasRole('COORDINADOR')")
@RequiredArgsConstructor
public class DonacionService implements IDonacionService {

    private final IDonacionRepository donacionRepository;
    private final IEventoSolidarioService eventoSolidarioService;
    private final IInventarioService inventarioService;
    private final IUsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public DonacionDTO crearDonacion(CrearDonacionDTO crearDonacionDTO) {
        // Valido que el evento exista
    	EventoSolidario evento = eventoSolidarioService.obtenerEntidadPorId(crearDonacionDTO.getIdEventoSolidario());

        // Obtengo el inventario por ID
        Inventario inventario = inventarioService.obtenerInventarioPorId(crearDonacionDTO.getIdInventario());

        // Valido que haya stock suficiente
        if (inventario.getCantidad() < crearDonacionDTO.getCantidad()) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + inventario.getCantidad() + ", Solicitado: " + crearDonacionDTO.getCantidad());
        }

        //Obtenemos el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userData = (UserDetails) auth.getPrincipal();
        Optional<Usuario> usuarioAutenticado = usuarioRepository.findByNombreUsuario(userData.getUsername());

        // Actualizo el inventario
        inventario.setCantidad(inventario.getCantidad() - crearDonacionDTO.getCantidad()); // Descuento la cantidad
        inventario.setFechaHoraModificacion(LocalDateTime.now()); // fecha
        inventario.setUsuarioModificacion(usuarioAutenticado.get()); // usuario

        inventario = inventarioService.actualizarInventario(inventario);

        //Creo y guardo la donación
        Donacion donacion = Donacion.builder()
                .fechaHoraModificacion(LocalDateTime.now())
                .cantidad(crearDonacionDTO.getCantidad())
                .eventoSolidario(evento)
                .usuario(usuarioAutenticado.get())
                .inventario(inventario)
                .build();

        Donacion donacionGuardada = donacionRepository.save(donacion);
        return DonacionMapper.aDTO(donacionGuardada);
    }

    /// obtengo todas las donaciones asociadas a un evento solidario específico
    @Override
    @Transactional
    public List<DonacionDTO> traerDonacionesPorEvento(Long idEventoSolidario) {
        List<Donacion> donaciones = donacionRepository.findByEventoSolidarioIdEventoSolidarioConInventario(idEventoSolidario);
        return donaciones.stream()
                .map(DonacionMapper::aDTO)
                .collect(Collectors.toList());
    }
}