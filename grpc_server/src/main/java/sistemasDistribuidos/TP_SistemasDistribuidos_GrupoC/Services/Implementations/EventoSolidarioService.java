package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.impl;

import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoSolidarioDTO;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Estado;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.EventoSolidarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.EventoSolidarioDTOMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.EventoSolidarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.UsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.IEventoSolidarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service("eventoSolidarioService")
@RequiredArgsConstructor
public class EventoSolidarioService implements IEventoSolidarioService {

    private final EventoSolidarioRepository eventoSolidarioRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    /// creo un evento solidario
    public void crearEventoSolidario(CrearEventoSolidarioDTO dto) {
        /// valido que la fecha sea posterior a la actual
        if (dto.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha del evento debe ser posterior a la fecha actual.");
        }

        /// valido que los miembros no esten deshabilitados
        List<String> miembros = dto.getMiembros().stream()
                .map(miembro -> miembro.getEmail())
                .collect(Collectors.toList());

        List<Usuario> usuariosEncontrados = usuarioRepository.findByEmailInAndEstado(miembros, true);

        if (usuariosEncontrados.size() != miembros.size()) {
            throw new IllegalArgumentException("Uno o más miembros no están activos en el sistema.");
        }

        /// mapeo del DTO a Entidad y guardo en la base de datos
        EventoSolidario evento = EventoSolidarioMapper.aEntidad(dto);
        evento.setMiembros(usuariosEncontrados);
        eventoSolidarioRepository.save(evento);
    }

    @Override
    @Transactional
    /// modifico un evento solidario
    public void modificarEventoSolidario(ModificarEventoSolidarioDTO dto) {
        Optional<EventoSolidario> eventoOpt = eventoSolidarioRepository.findById(dto.getIdEventoSolidario;
        /// valido si encontro o no el evento
        if (!eventoOpt.isPresent()) {
            throw new IllegalArgumentException("Evento solidario no encontrado.");
        }

        EventoSolidario evento = eventoOpt.get();

        /// valido que la fecha sea posterior a la actual
        if (dto.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha del evento debe ser posterior a la fecha actual.");
        }

        /// valido que los miembros no esten inactivos
        List<String> miembros = dto.getMiembros().stream()
                .map(miembro -> miembro.getEmail())
                .collect(Collectors.toList());

        List<Usuario> usuariosEncontrados = usuarioRepository.findByEmailInAndEstado(miembros, true);

        if (usuariosEncontrados.size() != miembros.size()) {
            throw new IllegalArgumentException("Uno o más miembros no están activos en el sistema.");
        }


        evento.setNombre(dto.getNombre());
        evento.setFechaHora(dto.getFechaHora());
        evento.setMiembros(usuariosEncontrados);
        /// guardo los nuevos cambios
        eventoSolidarioRepository.save(evento);
    }

    @Override
    @Transactional
    /// elimino un evento soldiario
    public void eliminarEventoSolidario(Long idEventoSolidario) {
        Optional<EventoSolidario> eventoOpt = eventoSolidarioRepository.findById(idEventoSolidario);
        if (!eventoOpt.isPresent()) {
            throw new IllegalArgumentException("Evento solidario no encontrado.");
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
    }

    @Override
    /// Obtengo todos los eventos solidarios
    public List<EventoSolidarioDTO> obtenerTodos() {
        return eventoSolidarioRepository.findAll().stream()
                .map(EventoSolidarioDTOMapper::aDTO)
                .collect(Collectors.toList());
    }

    @Override
    /// obtengo un evento solidario por ID
    public EventoSolidarioDTO obtenerPorId(Long idEventoSolidario) {
        Optional<EventoSolidario> eventoOpt = eventoSolidarioRepository.findById(idEventoSolidario);
        if (!eventoOpt.isPresent()) {
            throw new IllegalArgumentException("Evento solidario no encontrado.");
        }
        return EventoSolidarioDTOMapper.aDTO(eventoOpt.get());
    }
}
