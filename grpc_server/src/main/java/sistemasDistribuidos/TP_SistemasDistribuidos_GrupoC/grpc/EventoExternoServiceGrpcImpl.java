package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.services.evento_externo.AdhesionParticipanteInternoRequestProto;
import proto.services.evento_externo.EventoExternoServiceGrpc;
import proto.services.kafka.BajaEventoKafkaProto;
import proto.services.kafka.PublicacionEventoKafkaProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoExternoDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.EventoExternoMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations.EventoExternoService;

@GrpcService
@RequiredArgsConstructor
public class EventoExternoServiceGrpcImpl extends EventoExternoServiceGrpc.EventoExternoServiceImplBase {
    ///Atributo:
    private final EventoExternoService eventoExternoService;

    ///Eliminar evento:
    @Override
    public void eliminarEventoExterno(BajaEventoKafkaProto request, StreamObserver<Empty> responseObserver) {
        try {
            eventoExternoService.eliminarEventoExterno(request.getIdEvento(), request.getIdOrganizacion());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription("Evento externo no encontrado con id: " + request.getIdEvento())
                            .asRuntimeException()
            );
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    ///Adherir participante interno:
    @Override
    public void adherirParticipanteInterno(AdhesionParticipanteInternoRequestProto request, StreamObserver<Empty> responseObserver) {
        try {
            eventoExternoService.adherirParticipanteInterno(request.getIdEvento(), request.getIdOrganizador(), request.getEmailParticipante());

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    ///Crear evento externo:
    @Override
    public void crearEventoExterno(PublicacionEventoKafkaProto request, StreamObserver<Empty> responseObserver) {
        try {
            EventoExternoDTO dto = EventoExternoMapper.aDTO(request);
            eventoExternoService.crearEventoExterno(dto);

            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } catch (EntityExistsException e) {
            responseObserver.onError(
                    io.grpc.Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
