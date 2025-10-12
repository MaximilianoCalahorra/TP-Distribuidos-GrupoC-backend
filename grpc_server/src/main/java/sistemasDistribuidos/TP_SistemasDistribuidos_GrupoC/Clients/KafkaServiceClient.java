package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.services.kafka.KafkaServiceGrpc;
import proto.services.kafka.KafkaServiceGrpc.KafkaServiceBlockingStub;
import proto.services.kafka.*;

@Component
public class KafkaServiceClient {
    ///Atributo:
    private final KafkaServiceBlockingStub blockingStub;

    ///Constructor:
    public KafkaServiceClient(@Value("${kafka.service.grpc_server}") String host, @Value("${kafka.service.grpc_server.port}") int port) {
        //Conexión con el servidor gRPC de Kafka Service:
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port) //URL del servidor gRPC de Kafka Service.
                .usePlaintext()
                .build();

        blockingStub = KafkaServiceGrpc.newBlockingStub(channel); //Construcción del cliente gRPC.
    }

    ///Métodos para comunicarse con el Kafka Service:

    //Publicar baja de un evento:
    public void publicarBajaEvento(BajaEventoKafkaProto proto) {
        blockingStub.publicarBajaEvento(proto);
    }

    //Publicar la adhesión de un participante interno a un evento externo:
    public void publicarAdhesionParticipanteInterno(AdhesionVoluntarioExternoRequestProto proto) {
        blockingStub.publicarAdhesionParticipanteInterno(proto);
    }
    
    //Publicar evento solidario:
    public void publicarEventoSolidario(PublicacionEventoKafkaProto proto) {
        blockingStub.publicarEventoSolidario(proto);
    }
    
    //Publicar solicitud de donación interna:
    public void publicarSolicitudDonacion(PublicacionSolicitudDonacionKafkaProto proto) {
        blockingStub.publicarSolicitudDonacion(proto);
    }
}