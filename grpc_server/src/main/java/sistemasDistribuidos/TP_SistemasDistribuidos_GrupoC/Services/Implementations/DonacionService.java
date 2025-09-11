package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.DonacionMapper;
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
    public DonacionDTO crearDonacion(CrearDonacionDTO crearDonacionDTO) {
        EventoSolidario evento = eventoSolidarioService.obtenerEventoPorId(crearDonacionDTO.getIdEvento());
        Usuario usuario = usuarioService.getUsuarioLogueado();

        Donacion donacion = Donacion.builder()
                .fechaHoraModificacion(LocalDateTime.now())
                .categoria(crearDonacionDTO.getCategoria())
                .descripcion(crearDonacionDTO.getDescripcion())
                .cantidad(crearDonacionDTO.getCantidad())
                .eventoSolidario(evento)
                .usuario(usuario)
                .build();

        Donacion donacionGuardada = donacionRepository.save(donacion);
        return DonacionMapper.aDTO(donacionGuardada);
    }

    @Override
    public List<DonacionDTO> traerDonacionesPorEvento(Long idEvento) {
        List<Donacion> donaciones = donacionRepository.findByEventoSolidarioIdEvento(idEvento);

        return donaciones.stream()
                .map(DonacionMapper::aDTO)
                .collect(Collectors.toList());
    }
}