package tpSistemasDistribuidos.kafkaService.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import proto.dtos.voluntario_externo.VoluntarioExternoProto;
import proto.services.kafka.AdhesionVoluntarioExternoRequestProto;
import tpSistemasDistribuidos.kafkaService.clients.EventoSolidarioServiceGrpcClient;

@Service
@RequiredArgsConstructor
public class AdhesionVoluntarioExternoConsumer {
    ///Atributos:
    private final EventoSolidarioServiceGrpcClient grpcClient; //Cliente gRPC en Kafka Service hacia servidor gRPC.

    @Autowired
    private ObjectMapper mapper;

    @KafkaListener(topics = "adhesion-evento-1")
    public void consumirAdhesionVoluntarioExterno(String mensajeJson) {
        try {
            JsonNode root = mapper.readTree(mensajeJson);

            //Extraer voluntario manualmente:
            JsonNode voluntarioNode = root.get("voluntario");

            //Crear el voluntario externo proto:
            VoluntarioExternoProto voluntarioProto = VoluntarioExternoProto.newBuilder()
                    .setIdVoluntario(voluntarioNode.get("idVoluntario").asText())
                    .setNombre(voluntarioNode.get("nombre").asText())
                    .setApellido(voluntarioNode.get("apellido").asText())
                    .setTelefono(voluntarioNode.get("telefono").asText())
                    .setEmail(voluntarioNode.get("email").asText())
                    .setIdOrganizacion(voluntarioNode.get("idOrganizacion").asText())
                    .build();

            //Crear el request proto principal:
            AdhesionVoluntarioExternoRequestProto proto = AdhesionVoluntarioExternoRequestProto.newBuilder()
                    .setIdEvento(root.get("idEvento").asText())
                    .setVoluntario(voluntarioProto)
                    .build();

            //Invocar gRPC para adherir el voluntario externo al evento en nuestra base de datos:
            grpcClient.adherirVoluntarioExterno(proto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
