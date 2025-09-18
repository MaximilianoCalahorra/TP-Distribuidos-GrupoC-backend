package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.MiembroDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.EventoSolidarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.UsuarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IEventoSolidarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IUsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IUsuarioService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IEventoSolidarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service("eventoSolidarioService")
@RequiredArgsConstructor
public class EventoSolidarioService implements IEventoSolidarioService {

    private final IEventoSolidarioRepository eventoSolidarioRepository;
    private final IUsuarioRepository usuarioRepository;

    @Override
    @Transactional
    /// creo un evento solidario
    public EventoSolidarioDTO crearEventoSolidario(CrearEventoSolidarioDTO dto) {
        /// valido que la fecha sea posterior a la actual
        if (dto.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha del evento debe ser posterior a la fecha actual.");
        }

        /// valido que los miembros no esten deshabilitados
        for (MiembroDTO miembro : dto.getMiembros()) {
            Optional<Usuario> usuario = usuarioRepository.findByEmailAndEstado(miembro.getEmail(), false);
            if (usuario.isPresent()) {
                throw new IllegalArgumentException("Uno o más miembros no están activos en el sistema.");
            }
        }

        List<Usuario> miembros = dto.getMiembros().stream().map(UsuarioMapper::aEntidad).collect(Collectors.toList());

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
            throw new IllegalArgumentException("Evento solidario no encontrado.");
        }

        EventoSolidario evento = eventoOpt.get();

        /// valido que la fecha sea posterior a la actual
        if (dto.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha del evento debe ser posterior a la fecha actual.");
        }

        /// valido que los miembros no esten inactivos
        for (MiembroDTO miembro : dto.getMiembros()) {
            Optional<Usuario> usuario = usuarioRepository.findByEmailAndEstado(miembro.getEmail(), false);
            if (usuario.isPresent()) {
                throw new IllegalArgumentException("Uno o más miembros no están activos en el sistema.");
            }
        }

        List<Usuario> miembros = dto.getMiembros().stream().map(UsuarioMapper::aEntidad).collect(Collectors.toList());

        if (miembros.size() != miembros.size()) {
            throw new IllegalArgumentException("Uno o más miembros no están activos en el sistema.");
        }


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
        return true;
    }

    @Override
    /// Obtengo todos los eventos solidarios
    public List<EventoSolidarioDTO> obtenerTodos() {
        return eventoSolidarioRepository.findAll().stream()
                .map(EventoSolidarioMapper::aEventoSolidarioDTO)
                .collect(Collectors.toList());
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

}