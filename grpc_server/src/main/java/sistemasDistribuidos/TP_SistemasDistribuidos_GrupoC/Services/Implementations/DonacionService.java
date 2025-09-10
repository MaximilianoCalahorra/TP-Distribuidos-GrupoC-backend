package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.DonacionRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.IEventoSolidarioService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.IUsuarioService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.IDonacionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonacionService implements IDonacionService {

    private final DonacionRepository donacionRepository;
    private final IEventoSolidarioService eventoSolidarioService;
    private final IUsuarioService usuarioService;

    @Override
    @Transactional
    public void crearDonacion(CrearDonacionDTO crearDonacionDTO, Long idUsuario) {
        EventoSolidario evento = eventoSolidarioService.obtenerEventoPorId(crearDonacionDTO.getIdEvento());
        Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);

        Donacion donacion = Donacion.builder()
                .fechaHoraModificacion(LocalDateTime.now())
                .categoria(crearDonacionDTO.getCategoria())
                .descripcion(crearDonacionDTO.getDescripcion())
                .cantidad(crearDonacionDTO.getCantidad())
                .eventoSolidario(evento)
                .usuario(usuario)
                .build();

        donacionRepository.save(donacion);
    }

    @Override
    public List<DonacionDTO> traerDonacionesPorEvento(Long idEvento) {
        eventoSolidarioService.obtenerEventoPorId(idEvento);
        List<Donacion> donaciones = donacionRepository.findByEventoSolidarioIdEventoSolidario(idEvento);

        return donaciones.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private DonacionDTO convertirADTO(Donacion donacion) {
        return new DonacionDTO(
                donacion.getCategoria(),
                donacion.getCantidad(),
                donacion.getDescripcion()
        );
    }
}
