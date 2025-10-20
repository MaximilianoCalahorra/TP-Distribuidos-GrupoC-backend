package tpSistemasDistribuidos.kafkaService.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.google.protobuf.util.JsonFormat;

import lombok.RequiredArgsConstructor;
import proto.services.kafka.BajaSolicitudDonacionKafkaProto.Builder;
import proto.services.kafka.BajaSolicitudDonacionKafkaProto;
import tpSistemasDistribuidos.kafkaService.clients.SolicitudDonacionServiceGrpcClient;

@Service
@RequiredArgsConstructor
public class BajaSolicitudDonacionConsumer {
    ///Atributos:
    private final SolicitudDonacionServiceGrpcClient grpcClient; //Cliente gRPC en Kafka Service hacia servidor gRPC.

    @KafkaListener(topics = "baja-solicitud-donaciones")
    public void consumirBajaSolicitudDonacion(String mensajeJson) {
        try {
            //Construir el builder del proto:
            Builder builder = BajaSolicitudDonacionKafkaProto.newBuilder();

            //Parsear el JSON recibido al proto:
            JsonFormat.parser().merge(mensajeJson, builder);
            BajaSolicitudDonacionKafkaProto proto = builder.build();

            //Ignorar si el evento proviene de mi propia organización:
            if (proto.getIdOrganizacion().equals("1")) {
                return;
            }

            //Invocar gRPC para dar de baja el evento en nuestra base de datos:
            grpcClient.procesarBajaSolicitudDonacionExterna(proto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
