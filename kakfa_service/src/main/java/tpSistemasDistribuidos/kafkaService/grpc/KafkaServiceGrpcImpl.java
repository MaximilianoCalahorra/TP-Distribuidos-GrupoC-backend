package tpSistemasDistribuidos.kafkaService.grpc;

import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import proto.services.kafka.KafkaServiceGrpc;
import proto.services.kafka.PublicacionEventoKafkaProto;
import tpSistemasDistribuidos.kafkaService.producer.KafkaProducerEvento;
import proto.services.kafka.AdhesionVoluntarioExternoRequestProto;
import proto.services.kafka.BajaEventoKafkaProto;
import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;

@GrpcService
public class KafkaServiceGrpcImpl extends KafkaServiceGrpc.KafkaServiceImplBase {
    ///Atributo:
    @Autowired
    private KafkaProducerEvento producer;

    @Override
    public void publicarBajaEvento(BajaEventoKafkaProto request, StreamObserver<Empty> responseObserver) {
        producer.publicarBajaEvento(request); //Enviar al topic de Kafka.
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void publicarAdhesionParticipanteInterno(AdhesionVoluntarioExternoRequestProto request, StreamObserver<Empty> responseObserver) {
        producer.publicarAdhesionParticipanteInterno(request); //Enviar al topic de Kafka.
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }

    @Override
    public void publicarEventoSolidario(PublicacionEventoKafkaProto request, StreamObserver<Empty> responseObserver) {
        producer.publicarEventoSolidario(request); //Enviar al topic de Kafka.
        responseObserver.onNext(Empty.getDefaultInstance());
        responseObserver.onCompleted();
    }
}
