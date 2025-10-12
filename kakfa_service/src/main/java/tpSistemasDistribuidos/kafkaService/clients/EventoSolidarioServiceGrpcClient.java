package tpSistemasDistribuidos.kafkaService.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.services.kafka.AdhesionVoluntarioExternoRequestProto;
import proto.services.evento_solidario.EventoSolidarioServiceGrpc;
import proto.services.evento_solidario.EventoSolidarioServiceGrpc.EventoSolidarioServiceBlockingStub;

@Component
public class EventoSolidarioServiceGrpcClient {
	///Atributo:
	private final EventoSolidarioServiceBlockingStub blockingStub;

	///Constructor:
    public EventoSolidarioServiceGrpcClient(@Value("${grpc_server_principal}") String host, @Value("${grpc_server_principal.port}") int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port) //URL del servidor gRPC.
                .usePlaintext()
                .build();

        blockingStub = EventoSolidarioServiceGrpc.newBlockingStub(channel); //Construcción del cliente gRPC.
    }

    ///Métodos para comunicarse con el servidor gRPC:
    
    //Adherir voluntario externo:
    public void adherirVoluntarioExterno(AdhesionVoluntarioExternoRequestProto request) {
    	blockingStub.adherirVoluntarioExterno(request);
    }
}
