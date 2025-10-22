package tpSistemasDistribuidos.kafkaService.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;

import lombok.RequiredArgsConstructor;
import proto.services.kafka.PublicacionOfertaDonacionKafkaProto;
import tpSistemasDistribuidos.kafkaService.clients.OfertaDonacionServiceGrpcClient;

@Service
@RequiredArgsConstructor
public class PublicacionOfertaDonacionConsumer {
	///Atributos:
    private final OfertaDonacionServiceGrpcClient grpcClient; //Cliente gRPC en Kafka Service hacia servidor gRPC.

    @KafkaListener(topics = "oferta-donaciones")
    public void consumirPublicacionSolicitudDonacion(String mensajeJson) {
    	try {  		
    		//Construir el builder del proto:
    		PublicacionOfertaDonacionKafkaProto.Builder builder = PublicacionOfertaDonacionKafkaProto.newBuilder();
            
            //Parsear el JSON recibido al proto:
            JsonFormat.parser().merge(mensajeJson, builder);
            PublicacionOfertaDonacionKafkaProto proto = builder.build();
            
            //Ignorar si el evento proviene de mi propia organización:
            if (proto.getIdOrganizacion().equals("1")) {
                return;
            }

            //Invocar gRPC para dar de alta la oferta externa en nuestra base de datos:
            grpcClient.crearOfertaDonacionExterna(proto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
