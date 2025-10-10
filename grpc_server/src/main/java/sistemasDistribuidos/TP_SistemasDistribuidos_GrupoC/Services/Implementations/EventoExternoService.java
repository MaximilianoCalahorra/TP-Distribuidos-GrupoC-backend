package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import proto.dtos.voluntario_externo.VoluntarioExternoProto;
import proto.services.kafka.AdhesionVoluntarioExternoRequestProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Clients.KafkaServiceClient;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.MiembroDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoExterno;
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
    public void eliminarEventoExterno(Long idEventoExterno) {
    	//Buscar evento por id:
    	Optional<EventoExterno> eventoOpt = eventoExternoRepository.findById(idEventoExterno);
    	
    	//Si el evento no existe:
        if (!eventoOpt.isPresent()) {
            throw new EntityNotFoundException("Evento solidario no encontrado.");
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
    public void adherirParticipanteInterno(Long idEventoExterno, String idOrganizador, MiembroDTO participanteInterno) {
    	//Buscar el evento:
    	EventoExterno evento = eventoExternoRepository.findById(idEventoExterno)
                .orElseThrow(() -> new EntityNotFoundException("Evento externo no encontrado."));
        
    	//Obtener miembros de nuestra ONG en el evento:
    	List<Usuario> participantesInternos = 
                Optional.ofNullable(evento.getParticipantesInternos()).orElse(new ArrayList<>());

    	//Verificar si ya pertenece al evento:
    	boolean yaAsociado = participantesInternos.stream()
                .anyMatch(v -> v.getEmail().equalsIgnoreCase(participanteInterno.getEmail()));
    	
    	//Si pertenece...
    	if (yaAsociado) {
            throw new IllegalArgumentException("El participante interno ya pertenece al evento externo."); //Mensaje informativo.
        }
        
    	//Si no pertenece, convertir a entidad:
    	Usuario participanteInternoEntidad = usuarioRepository.findByNombreUsuarioOrEmail("", participanteInterno.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Participante interno no encontrado."));
        
        //Agregar al listado de participantes internos:
        participantesInternos.add(participanteInternoEntidad);
        
        //Reemplazar el listado de participantes internos del evento externo con el nuevo:
        evento.setParticipantesInternos(participantesInternos);
        
        //Persistir el evento:
        eventoExternoRepository.save(evento);
        
        //Intentar publicar en Kafka la adhesión del participante interno al evento externo:
        try {
        	//Mapear participante interno al proto de voluntario externo:
            VoluntarioExternoProto participanteProto = VoluntarioExternoProto.newBuilder()
                    .setIdVoluntarioExterno(participanteInternoEntidad.getIdUsuario())
                    .setNombre(participanteInterno.getNombre())
                    .setApellido(participanteInterno.getApellido())
                    .setTelefono(participanteInterno.getTelefono())
                    .setEmail(participanteInterno.getEmail())
                    .setIdOrganizacion(ongEmpujeComunitarioId)
                    .build();

            //Construir request proto:
            AdhesionVoluntarioExternoRequestProto proto = AdhesionVoluntarioExternoRequestProto.newBuilder()
                    .setIdEventoSolidario(idEventoExterno)
                    .setIdOrganizador(idOrganizador)
                    .setVoluntarioExterno(participanteProto)
                    .build();

            //Llamar al Kafka Service via gRPC:
            kafkaServiceClient.publicarAdhesionParticipanteInterno(proto);

        } catch (Exception e) {
            System.err.println("Error publicando adhesión del participante interno en Kafka: " + e);
        }
    }
}
