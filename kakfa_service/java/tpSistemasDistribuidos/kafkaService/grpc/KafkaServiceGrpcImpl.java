package tpSistemasDistribuidos.kakfaService.grpc;

import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import proto.services.kafka.KafkaServiceGrpc;
import proto.services.kafka.BajaEventoKafkaProto;
import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import tpSistemasDistribuidos.kakfaService.producer.KafkaProducerEvento;

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
}
