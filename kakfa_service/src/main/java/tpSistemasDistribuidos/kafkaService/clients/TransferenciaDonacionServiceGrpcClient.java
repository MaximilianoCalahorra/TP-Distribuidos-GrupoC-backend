package tpSistemasDistribuidos.kafkaService.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.services.kafka.PublicacionTransferenciaDonacionKafkaProto;
import proto.services.transferencia_donacion.TransferenciaDonacionServiceGrpc;
import proto.services.transferencia_donacion.TransferenciaDonacionServiceGrpc.TransferenciaDonacionServiceBlockingStub;

@Component
public class TransferenciaDonacionServiceGrpcClient {
	///Atributo:
	private final TransferenciaDonacionServiceBlockingStub blockingStub;

	///Constructor:
    public TransferenciaDonacionServiceGrpcClient(@Value("${grpc_server_principal}") String host, @Value("${grpc_server_principal.port}") int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port) //URL del servidor gRPC.
                .usePlaintext()
                .build();

        blockingStub = TransferenciaDonacionServiceGrpc.newBlockingStub(channel); //Construcción del cliente gRPC.
    }

    ///Métodos para comunicarse con el servidor gRPC:
    
    //Registrar transferencia recibida:
    public void crearTransferenciaEntrante(PublicacionTransferenciaDonacionKafkaProto request) {
    	blockingStub.crearTransferenciaEntrante(request);
    }
}
