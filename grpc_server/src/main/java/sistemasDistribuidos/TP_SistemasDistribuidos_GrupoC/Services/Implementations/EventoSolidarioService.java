package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.*;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.DonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.UsuarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.EventoSolidarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IDonacionRepository;
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
@PreAuthorize("hasRole('PRESIDENTE') or hasRole('COORDINADOR')or hasRole('VOLUNTARIO')")
@RequiredArgsConstructor
public class EventoSolidarioService implements IEventoSolidarioService {

    private final IEventoSolidarioRepository eventoSolidarioRepository;
    private final IUsuarioRepository usuarioRepository;
    private final IDonacionRepository donacionRepository;
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
        Optional<EventoSolidario> eventoOpt = eventoSolidarioRepository.getByIdEvento(dto.getIdEventoSolidario());
        /// valido si encontro o no el evento
        if (!eventoOpt.isPresent()) {
            throw new EntityNotFoundException("Evento solidario no encontrado.");
        }

        EventoSolidario evento = eventoOpt.get();

        /// valido y obtengo usuarios activos comparando con el listado del evento
        List<Usuario> miembros = validarYObtenerUsuariosActivos(dto.getMiembros());

        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setFechaHora(dto.getFechaHora());
        evento.setMiembros(miembros);
        /// guardo los nuevos cambios
        eventoSolidarioRepository.save(evento);
        return EventoSolidarioMapper.aEventoSolidarioDTO(evento);
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

        // elimino las donaciones que tenga asociadas dicho evento
        List<Donacion> donaciones = donacionRepository.findByEventoSolidarioIdEventoSolidarioConInventario(evento.getIdEventoSolidario());
        for (Donacion donacion : donaciones) {
            donacionRepository.delete(donacion);
        }
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
        Optional<EventoSolidario> eventoOpt = eventoSolidarioRepository.getByIdEvento(idEventoSolidario);
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

    /// Participar de evento solidario
    @Override
    public EventoSolidarioDTO participarDeEventoSolidario(Long idEventoSolidario) {
        EventoSolidarioDTO eventoSolidarioDTO;

        //Obtenemos el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userData = (UserDetails) auth.getPrincipal();
        Optional<Usuario> usuarioAutenticado = usuarioRepository.findByNombreUsuario(userData.getUsername());

        //Buscamos el evento solidario en el cual se desea participar
        Optional<EventoSolidario> eventoSolidario = eventoSolidarioRepository.getByIdEvento(idEventoSolidario);

        //Si el evento existe
        if (eventoSolidario.isPresent()) {
            //Buscamos los eventos en los cuales se encuentre el usuario
            List <EventoSolidario> listaEventos = eventoSolidarioRepository.getEventsByNombreUsuario(usuarioAutenticado.get().getNombreUsuario());
            //Verificamos que no esté presente en el evento en el cual desea participar
            if (listaEventos.stream().anyMatch(evento -> evento.getIdEventoSolidario() == idEventoSolidario)) {
                throw new IllegalArgumentException("Ya participas del evento ingresado!");
            }
            //De no ser asi...
            //Verificamos que el evento al cual se desea inscribir no haya pasado
            if (eventoSolidario.get().getFechaHora().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("No puedes participar de este evento porque ya pasó!");
            }
            eventoSolidario.get().getMiembros().add(usuarioAutenticado.get());
            eventoSolidarioDTO = EventoSolidarioMapper.aEventoSolidarioDTO(eventoSolidario.get());
            eventoSolidarioRepository.save(eventoSolidario.get());
        } else {
            throw new IllegalArgumentException("El evento no existe!");
        }
        return eventoSolidarioDTO;
    }

    // Eliminar a un usuario de los eventos solidarios FUTUROS en los que este presente
    @Override
    public void eliminarUsuarioDeEventosSolidarios(String nombreUsuario) {
        List <EventoSolidario> listaEventos = eventoSolidarioRepository.listAllFutureEvents(LocalDateTime.now());
        for (EventoSolidario eventoSolidario : listaEventos) {
            boolean cambio = eventoSolidario.getMiembros()
                    .removeIf(u -> nombreUsuario.equals(u.getNombreUsuario()));
            if (cambio) {
                eventoSolidarioRepository.save(eventoSolidario);
            }
        }
    }

    /// Darse de baja de evento solidario
    @Override
    public EventoSolidarioDTO darseDeBajaDeEventoSolidario(Long idEventoSolidario) {
        EventoSolidarioDTO eventoSolidarioDTO;

        //Obtenemos el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userData = (UserDetails) auth.getPrincipal();
        Optional<Usuario> usuarioAutenticado = usuarioRepository.findByNombreUsuario(userData.getUsername());

        //Buscamos el evento solidario del cual el usuario desea bajarse
        Optional<EventoSolidario> eventoSolidario = eventoSolidarioRepository.getByIdEvento(idEventoSolidario);

        //Si el evento existe
        if (eventoSolidario.isPresent()) {
            //Buscamos los eventos en los cuales se encuentre el usuario
            List <EventoSolidario> listaEventos = eventoSolidarioRepository.getEventsByNombreUsuario(usuarioAutenticado.get().getNombreUsuario());
            //Verificamos que esté presente en el evento que se desea dar de baja
            if (listaEventos.stream().anyMatch(evento -> evento.getIdEventoSolidario() == idEventoSolidario)) {
                //De ser asi...
                //Verificamos que el evento al cual se desea inscribir no haya pasado
                if (eventoSolidario.get().getFechaHora().isBefore(LocalDateTime.now())) {
                    throw new IllegalArgumentException("No puedes darte de baja de este evento porque ya pasó!");
                }
                eventoSolidario.get().getMiembros().remove(usuarioAutenticado.get());
                eventoSolidarioDTO = EventoSolidarioMapper.aEventoSolidarioDTO(eventoSolidario.get());
                eventoSolidarioRepository.save(eventoSolidario.get());
            } else {
                throw new IllegalArgumentException("No participas del evento ingresado!");
            }
        } else {
            throw new IllegalArgumentException("El evento no existe!");
        }
        return eventoSolidarioDTO;
    }
}