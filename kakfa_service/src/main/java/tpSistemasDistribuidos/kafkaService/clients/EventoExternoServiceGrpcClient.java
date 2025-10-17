package tpSistemasDistribuidos.kafkaService.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.services.evento_externo.EventoExternoServiceGrpc;
import proto.services.evento_externo.EventoExternoServiceGrpc.EventoExternoServiceBlockingStub;
import proto.services.kafka.BajaEventoKafkaProto;
import proto.services.kafka.PublicacionEventoKafkaProto;

@Component
public class EventoExternoServiceGrpcClient {
    ///Atributo:
    private final EventoExternoServiceBlockingStub blockingStub;

    ///Constructor:
    public EventoExternoServiceGrpcClient(@Value("${grpc_server_principal}") String host, @Value("${grpc_server_principal.port}") int port) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port) //URL del servidor gRPC.
                .usePlaintext()
                .build();

        blockingStub = EventoExternoServiceGrpc.newBlockingStub(channel); //Construcción del cliente gRPC.
    }

    ///Métodos para comunicarse con el servidor gRPC:

    //Eliminar evento externo:
    public void eliminarEventoExterno(BajaEventoKafkaProto request) {
        blockingStub.eliminarEventoExterno(request);
    }

    //Crear evento externo:
    public void crearEventoExterno(PublicacionEventoKafkaProto request) {
        blockingStub.crearEventoExterno(request);
    }
}
