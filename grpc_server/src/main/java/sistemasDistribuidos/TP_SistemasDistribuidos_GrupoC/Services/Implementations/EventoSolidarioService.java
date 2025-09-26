package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.MiembroDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.UsuarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.EventoSolidarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IEventoSolidarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IUsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IEventoSolidarioService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service("eventoSolidarioService")
@PreAuthorize("hasRole('PRESIDENTE') or hasRole('COORDINADOR')")
@RequiredArgsConstructor
public class EventoSolidarioService implements IEventoSolidarioService {

    private final IEventoSolidarioRepository eventoSolidarioRepository;
    private final IUsuarioRepository usuarioRepository;
    private static final ZoneId ZONE_ARG = ZoneId.of("America/Argentina/Buenos_Aires");

    @Override
    @Transactional
    /// creo un evento solidario
    public EventoSolidarioDTO crearEventoSolidario(CrearEventoSolidarioDTO dto) {
        /// valido que la fecha sea posterior a la actual
        validarFechaEvento(dto.getFechaHora());

        /// valido y obtengo usuarios activos comparando con el listado del evento
        List<Usuario> miembros = validarYObtenerUsuariosActivos(dto.getMiembros());

        /// mapeo del DTO a Entidad y guardo en la base de datos
        EventoSolidario evento = EventoSolidarioMapper.aEntidad(dto);
        evento.setMiembros(miembros);

        return EventoSolidarioMapper.aEventoSolidarioDTO(eventoSolidarioRepository.save(evento));
    }

    @Override
    @Transactional
    /// modifico un evento solidario
    public EventoSolidarioDTO modificarEventoSolidario(ModificarEventoSolidarioDTO dto) {
        Optional<EventoSolidario> eventoOpt = eventoSolidarioRepository.findById(dto.getIdEventoSolidario());
        /// valido si encontro o no el evento
        if (!eventoOpt.isPresent()) {
            throw new EntityNotFoundException("Evento solidario no encontrado.");
        }

        EventoSolidario evento = eventoOpt.get();

        /// valido que la fecha sea posterior a la actual
        validarFechaEvento(dto.getFechaHora());

        /// valido y obtengo usuarios activos comparando con el listado del evento
        List<Usuario> miembros = validarYObtenerUsuariosActivos(dto.getMiembros());

        evento.setNombre(dto.getNombre());
        evento.setFechaHora(dto.getFechaHora());
        evento.setMiembros(miembros);
        /// guardo los nuevos cambios
        return EventoSolidarioMapper.aEventoSolidarioDTO(eventoSolidarioRepository.save(evento));
    }

    @Override
    @Transactional
    /// elimino un evento soldiario
    public boolean eliminarEventoSolidario(Long idEventoSolidario) {
        Optional<EventoSolidario> eventoOpt = eventoSolidarioRepository.findById(idEventoSolidario);
        if (!eventoOpt.isPresent()) {
            throw new EntityNotFoundException("Evento solidario no encontrado.");
        }

        EventoSolidario evento = eventoOpt.get();

        /// valido la fecha, ya que, solo se pueden eliminar eventos futuros
        if (evento.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Solo se pueden eliminar eventos a futuro.");
        }

        /// elimino la relacion con los miembros
        evento.getMiembros().clear();
        eventoSolidarioRepository.save(evento);

        /// elimino el evento
        eventoSolidarioRepository.delete(evento);
        return true;
    }

    @Override
    /// Obtengo todos los eventos solidarios
    public List<EventoSolidarioDTO> obtenerTodos() {
        List<EventoSolidario> listaEventosSolidarios = eventoSolidarioRepository.listAllEvents();
        List<EventoSolidarioDTO> listaEventosSolidariosDTO = new ArrayList<>();
        for (EventoSolidario eventoSolidario : listaEventosSolidarios) {
            EventoSolidarioDTO eventoSolidarioDTO = EventoSolidarioMapper.aEventoSolidarioDTO(eventoSolidario);
            listaEventosSolidariosDTO.add(eventoSolidarioDTO);
            List<MiembroDTO> listMiembroDTO = eventoSolidario.getMiembros().stream().map(usuario -> UsuarioMapper.aMiembroDTO(usuario)).toList();
            eventoSolidarioDTO.setMiembros(listMiembroDTO);
        }
        return listaEventosSolidariosDTO;
    }

    @Override
    /// obtengo un evento solidario por ID
    public EventoSolidarioDTO obtenerPorId(Long idEventoSolidario) {
        Optional<EventoSolidario> eventoOpt = eventoSolidarioRepository.findById(idEventoSolidario);
        if (!eventoOpt.isPresent()) {
            throw new IllegalArgumentException("Evento solidario no encontrado.");
        }
        return EventoSolidarioMapper.aEventoSolidarioDTO(eventoOpt.get());
    }

    @Override
    ///  obtengo una entidad evento solidario por ID
    public EventoSolidario obtenerEntidadPorId(Long idEventoSolidario) {
        return eventoSolidarioRepository.findById(idEventoSolidario)
                .orElseThrow(() -> new IllegalArgumentException("Evento solidario no encontrado."));
    }

    /// valido y obtengo usuarios activos comparando con un listado
    private List<Usuario> validarYObtenerUsuariosActivos(List<MiembroDTO> miembrosDto) {
        List<String> emails = miembrosDto.stream().map(MiembroDTO::getEmail).toList();
        List<Usuario> usuarios = usuarioRepository.findAllByEmailIn(emails);

        if (usuarios.size() != emails.size()) {
            throw new IllegalArgumentException("Uno o más miembros no existen en el sistema.");
        }
        if (usuarios.stream().anyMatch(u -> !u.isActivo())) {
            throw new IllegalArgumentException("Uno o más miembros no están activos en el sistema.");
        }
        return usuarios;
    }

    /// valido la fecha del evento
    private void validarFechaEvento(LocalDateTime fechaEvento) {
        LocalDateTime ahora = LocalDateTime.now(ZONE_ARG);

        if (fechaEvento.isBefore(ahora)) {
            throw new IllegalArgumentException("La fecha del evento debe ser posterior a la fecha actual.");
        }
    }
}