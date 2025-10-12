package tpSistemasDistribuidos.kafkaService.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;

import lombok.RequiredArgsConstructor;
import proto.services.kafka.BajaEventoKafkaProto;
import proto.services.kafka.BajaEventoKafkaProto.Builder;
import tpSistemasDistribuidos.kafkaService.clients.EventoExternoServiceGrpcClient;

@Service
@RequiredArgsConstructor
public class BajaEventoConsumer {
	///Atributos:
    private final EventoExternoServiceGrpcClient grpcClient; //Cliente gRPC en Kafka Service hacia servidor gRPC.

    @KafkaListener(topics = "baja-evento-solidario")
    public void consumirBajaEvento(String mensajeJson) {
    	try {
    		//Construir el builder del proto:
            Builder builder = BajaEventoKafkaProto.newBuilder();
            
            //Parsear el JSON recibido al proto:
            JsonFormat.parser().merge(mensajeJson, builder);
            BajaEventoKafkaProto proto = builder.build();

            //Invocar gRPC para dar de baja el evento en nuestra base de datos:
            grpcClient.eliminarEventoExterno(proto.getIdEvento());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
