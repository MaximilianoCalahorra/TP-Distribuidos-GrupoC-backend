package tpSistemasDistribuidos.kafkaService.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.services.kafka.PublicacionOfertaDonacionKafkaProto;
import proto.services.oferta_donacion.OfertaDonacionServiceGrpc;
import proto.services.oferta_donacion.OfertaDonacionServiceGrpc.OfertaDonacionServiceBlockingStub;

@Component
public class OfertaDonacionServiceGrpcClient {
	///Atributo:
	private final OfertaDonacionServiceBlockingStub blockingStub;

	///Constructor:
    public OfertaDonacionServiceGrpcClient(@Value("${grpc_server_principal}") String host, @Value("${grpc_server_principal.port}") int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port) //URL del servidor gRPC.
                .usePlaintext()
                .build();

        blockingStub = OfertaDonacionServiceGrpc.newBlockingStub(channel); //Construcción del cliente gRPC.
    }

    ///Métodos para comunicarse con el servidor gRPC:
    
    //Crear oferta de donación externa:
    public void crearOfertaDonacionExterna(PublicacionOfertaDonacionKafkaProto request) {
    	blockingStub.crearOfertaDonacionExterna(request);
    }
}
