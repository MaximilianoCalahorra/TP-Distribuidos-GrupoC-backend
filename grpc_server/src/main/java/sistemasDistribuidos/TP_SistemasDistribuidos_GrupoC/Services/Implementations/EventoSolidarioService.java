package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.EventoSolidarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.IEventoSolidarioService;

@Service
@RequiredArgsConstructor
public class EventoSolidarioService implements IEventoSolidarioService {

    private final EventoSolidarioRepository eventoRepository;

    @Override
    public EventoSolidario obtenerEventoPorId(Long idEvento) {
        return eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + idEvento));
    }
}
