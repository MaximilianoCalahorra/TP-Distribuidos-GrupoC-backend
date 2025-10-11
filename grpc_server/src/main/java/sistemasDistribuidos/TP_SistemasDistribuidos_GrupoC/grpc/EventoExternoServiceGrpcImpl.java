package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.services.evento_externo.AdhesionParticipanteInternoRequestProto;
import proto.services.evento_externo.EventoExternoServiceGrpc;
import proto.services.evento_externo.IdEventoExternoRequestProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations.EventoExternoService;

@GrpcService
@RequiredArgsConstructor
public class EventoExternoServiceGrpcImpl extends EventoExternoServiceGrpc.EventoExternoServiceImplBase {
	///Atributo:
	private final EventoExternoService eventoExternoService;
	
	///Eliminar evento:
    @Override
    public void eliminarEventoExterno(IdEventoExternoRequestProto request, StreamObserver<Empty> responseObserver) {
        try {
            eventoExternoService.eliminarEventoExterno(request.getIdEventoExterno());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription("Evento externo no encontrado con id: " + request.getIdEventoExterno())
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
            eventoExternoService.adherirParticipanteInterno(request.getIdEventoExterno(), request.getIdOrganizador(), request.getEmailParticipante());
            
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
}
