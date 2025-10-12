package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import proto.dtos.voluntario_externo.VoluntarioExternoProto;
import proto.services.kafka.AdhesionVoluntarioExternoRequestProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Clients.KafkaServiceClient;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoExternoDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.MiembroDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.EventoExternoMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.EventoSolidarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.UsuarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoExterno;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IEventoExternoRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IUsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IEventoExternoService;

@Service
@RequiredArgsConstructor
public class EventoExternoService implements IEventoExternoService {
    ///Atributos:
    private final IEventoExternoRepository eventoExternoRepository;
    private final KafkaServiceClient kafkaServiceClient;
    private final IUsuarioRepository usuarioRepository;
    @Value("${ong.id}")
    private String ongEmpujeComunitarioId;

    ///Eliminar evento:
    @Override
    @Transactional
    public void eliminarEventoExterno(String idEventoOrigen, String idOrganizacion) {
        //Buscar evento por id:
        Optional<EventoExterno> eventoOpt = eventoExternoRepository.findByIdEventoOrigenAndIdOrganizacion(idEventoOrigen, idOrganizacion);

        //Si el evento no existe:
        if (!eventoOpt.isPresent()) {
            throw new EntityNotFoundException("Evento externo no encontrado.");
        }

        //Obtener el evento:
        EventoExterno evento = eventoOpt.get();

        //Eliminar relación con los participantes:
        evento.getParticipantesInternos().clear();
        eventoExternoRepository.save(evento);

        //Eliminar el evento:
        eventoExternoRepository.delete(evento);
    }

    //Adherir participante interno:
    @Override
    @Transactional
    public void adherirParticipanteInterno(String idEvento, String idOrganizador, String emailParticipante) {
        //Buscar el evento:
        EventoExterno evento = eventoExternoRepository.findByIdEventoOrigenAndIdOrganizacion(idEvento, idOrganizador)
                .orElseThrow(() -> new EntityNotFoundException("Evento externo no encontrado."));

        //Obtener miembros de nuestra ONG en el evento:
        List<Usuario> participantesInternos =
                Optional.ofNullable(evento.getParticipantesInternos()).orElse(new ArrayList<>());

        //Verificar si ya pertenece al evento:
        boolean yaAsociado = participantesInternos.stream()
                .anyMatch(v -> v.getEmail().equalsIgnoreCase(emailParticipante));

        //Si pertenece...
        if (yaAsociado) {
            throw new IllegalArgumentException("El participante interno ya pertenece al evento externo."); //Mensaje informativo.
        }

        //Si no pertenece, convertir a entidad:
        Usuario entidad = usuarioRepository.findByNombreUsuarioOrEmail("", emailParticipante)
                .orElseThrow(() -> new EntityNotFoundException("Participante interno no encontrado."));

        //Agregar al listado de participantes internos:
        participantesInternos.add(entidad);

        //Reemplazar el listado de participantes internos del evento externo con el nuevo:
        evento.setParticipantesInternos(participantesInternos);

        //Persistir el evento:
        eventoExternoRepository.save(evento);

        //Intentar publicar en Kafka la adhesión del participante interno al evento externo:
        try {
            //Mapear participante interno al proto de voluntario externo:
            VoluntarioExternoProto participanteProto = VoluntarioExternoProto.newBuilder()
                    .setIdVoluntario(String.valueOf(entidad.getIdUsuario()))
                    .setNombre(entidad.getNombre())
                    .setApellido(entidad.getApellido())
                    .setTelefono(entidad.getTelefono())
                    .setEmail(entidad.getEmail())
                    .setIdOrganizacion(ongEmpujeComunitarioId)
                    .build();

            //Construir request proto:
            AdhesionVoluntarioExternoRequestProto proto = AdhesionVoluntarioExternoRequestProto.newBuilder()
                    .setIdEvento(idEvento)
                    .setIdOrganizador(idOrganizador)
                    .setVoluntario(participanteProto)
                    .build();

            //Llamar al Kafka Service via gRPC:
            kafkaServiceClient.publicarAdhesionParticipanteInterno(proto);

        } catch (Exception e) {
            System.err.println("Error publicando adhesión del participante interno en Kafka: " + e);
        }
    }

    ///Crear evento externo:
    @Override
    @Transactional
    public void crearEventoExterno(EventoExternoDTO eventoExterno) {
        //Buscar evento por id:
        Optional<EventoExterno> eventoOpt = eventoExternoRepository.findByIdEventoOrigenAndIdOrganizacion(eventoExterno.getIdEventoOrigen(), eventoExterno.getIdOrganizacion());

        //Si el evento existe:
        if (eventoOpt.isPresent()) {
            throw new EntityExistsException("El evento externo ya existe.");
        }

        //Si no existe, lo persistimos:
        EventoExterno evento = EventoExternoMapper.aEntidad(eventoExterno);
        eventoExternoRepository.save(evento);
    }

    //Obtener todos los eventos externos:
    @Override
    @PreAuthorize("hasRole('PRESIDENTE') or hasRole('COORDINADOR') or hasRole('VOLUNTARIO')")
    /// Obtengo todos los eventos solidarios
    public List<EventoExternoDTO> obtenerTodos() {
        List<EventoExterno> listEventosExternos = eventoExternoRepository.listAllEvents();
        List<EventoExternoDTO> listEventosExternosDTO = new ArrayList<>();
        for (EventoExterno eventoExterno : listEventosExternos) {
            EventoExternoDTO eventoExternoDTO = EventoExternoMapper.aDTO(eventoExterno);
            listEventosExternosDTO.add(eventoExternoDTO);
            List<MiembroDTO> listMiembroDTO = eventoExterno.getParticipantesInternos().stream().map(usuario -> UsuarioMapper.aMiembroDTO(usuario)).toList();
            eventoExternoDTO.setParticipantesInternos(listMiembroDTO);
        }
        return listEventosExternosDTO;
    }
}
