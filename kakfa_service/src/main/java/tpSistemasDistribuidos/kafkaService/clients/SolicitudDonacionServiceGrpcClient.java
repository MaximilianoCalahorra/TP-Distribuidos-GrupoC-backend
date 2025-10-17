package tpSistemasDistribuidos.kafkaService.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.services.kafka.PublicacionSolicitudDonacionKafkaProto;
import proto.services.solicitud_donacion.SolicitudDonacionServiceGrpc;
import proto.services.solicitud_donacion.SolicitudDonacionServiceGrpc.SolicitudDonacionServiceBlockingStub;

@Component
public class SolicitudDonacionServiceGrpcClient {
	///Atributo:
	private final SolicitudDonacionServiceBlockingStub blockingStub;

	///Constructor:
    public SolicitudDonacionServiceGrpcClient(@Value("${grpc_server_principal}") String host, @Value("${grpc_server_principal.port}") int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port) //URL del servidor gRPC.
                .usePlaintext()
                .build();

        blockingStub = SolicitudDonacionServiceGrpc.newBlockingStub(channel); //Construcción del cliente gRPC.
    }

    ///Métodos para comunicarse con el servidor gRPC:
    
    //Crear solicitud de donación externa:
    public void crearSolicitudDonacionExterna(PublicacionSolicitudDonacionKafkaProto request) {
    	blockingStub.crearSolicitudDonacionExterna(request);
    }
}
