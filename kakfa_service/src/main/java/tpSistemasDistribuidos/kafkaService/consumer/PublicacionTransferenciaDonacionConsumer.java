package tpSistemasDistribuidos.kafkaService.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;

import lombok.RequiredArgsConstructor;
import proto.services.kafka.PublicacionTransferenciaDonacionKafkaProto;
import tpSistemasDistribuidos.kafkaService.clients.TransferenciaDonacionServiceGrpcClient;

@Service
@RequiredArgsConstructor
public class PublicacionTransferenciaDonacionConsumer {
	///Atributos:
    private final TransferenciaDonacionServiceGrpcClient grpcClient; //Cliente gRPC en Kafka Service hacia servidor gRPC.

    @KafkaListener(topics = "transferencia-donaciones-1")
    public void consumirPublicacionTransferenciaDonacion(String mensajeJson) {
    	try {  		
    		//Construir el builder del proto:
    		PublicacionTransferenciaDonacionKafkaProto.Builder builder = PublicacionTransferenciaDonacionKafkaProto.newBuilder();
            
            //Parsear el JSON recibido al proto:
            JsonFormat.parser().merge(mensajeJson, builder);
            PublicacionTransferenciaDonacionKafkaProto proto = builder.build();
            
            //Invocar gRPC para dar de baja el evento en nuestra base de datos:
            grpcClient.crearTransferenciaEntrante(proto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
