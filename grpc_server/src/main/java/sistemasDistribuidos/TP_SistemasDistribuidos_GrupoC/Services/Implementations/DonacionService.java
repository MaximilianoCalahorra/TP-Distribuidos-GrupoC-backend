package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.DonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IDonacionRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.IEventoSolidarioService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IInventarioService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IDonacionService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IUsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonacionService implements IDonacionService {

    private final IDonacionRepository donacionRepository;
    private final IEventoSolidarioService eventoSolidarioService;
    private final IInventarioService inventarioService;
    private final IUsuarioService usuarioService;

    @Override
    public DonacionDTO crearDonacion(CrearDonacionDTO crearDonacionDTO) {
        // Valido que el evento exista
        EventoSolidario evento = eventoSolidarioService.obtenerPorId(crearDonacionDTO.getIdEventoSolidario());

        // Obtengo el inventario por ID
        Inventario inventario = inventarioService.obtenerInventarioPorId(crearDonacionDTO.getIdInventario());

        // Valido que haya stock suficiente
        if (inventario.getCantidad() < crearDonacionDTO.getCantidad()) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + inventario.getCantidad() + ", Solicitado: " + crearDonacionDTO.getCantidad());
        }

        //Obtengo usuario logueado para auditoría
        Usuario usuarioLogueado = usuarioService.getUsuarioLogueado();

        // Actualizo el inventario
        inventario.setCantidad(inventario.getCantidad() - crearDonacionDTO.getCantidad()); // Descuento la cantidad
        inventario.setFechaHoraModificacion(LocalDateTime.now()); // fecha
        inventario.setUsuarioModificacion(usuarioLogueado); // usuario


        inventarioService.actualizarInventario(inventario);

        //Creo y guardo la donación
        Donacion donacion = Donacion.builder()
                .fechaHoraModificacion(LocalDateTime.now())
                .cantidad(crearDonacionDTO.getCantidad())
                .eventoSolidario(evento)
                .usuario(usuarioLogueado)
                .build();

        Donacion donacionGuardada = donacionRepository.save(donacion);
        return DonacionMapper.aDTO(donacionGuardada);
    }

    /// obtengo todas las donaciones asociadas a un evento solidario específico
    @Override
    public List<DonacionDTO> traerDonacionesPorEvento(Long idEventoSolidario) {
        List<Donacion> donaciones = donacionRepository.findByEventoSolidarioIdEventoSolidario(idEventoSolidario);
        return donaciones.stream()
                .map(DonacionMapper::aDTO)
                .collect(Collectors.toList());
    }
}