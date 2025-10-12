package tpSistemasDistribuidos.kafkaService.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;

import lombok.RequiredArgsConstructor;
import proto.services.kafka.PublicacionEventoKafkaProto;
import tpSistemasDistribuidos.kafkaService.clients.EventoExternoServiceGrpcClient;

@Service
@RequiredArgsConstructor
public class PublicacionEventoConsumer {
	///Atributos:
    private final EventoExternoServiceGrpcClient grpcClient; //Cliente gRPC en Kafka Service hacia servidor gRPC.

    @KafkaListener(topics = "eventos-solidarios")
    public void consumirPublicacionEvento(String mensajeJson) {
    	try {
    		//Construir el builder del proto:
    		PublicacionEventoKafkaProto.Builder builder = PublicacionEventoKafkaProto.newBuilder();
            
            //Parsear el JSON recibido al proto:
            JsonFormat.parser().merge(mensajeJson, builder);
            PublicacionEventoKafkaProto proto = builder.build();
            
            //Ignorar si el evento proviene de mi propia organización:
            if (proto.getIdOrganizacion().equals("1")) {
                return;
            }

            //Invocar gRPC para dar de baja el evento en nuestra base de datos:
            grpcClient.crearEventoExterno(proto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
