package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;
import lombok.RequiredArgsConstructor;
import com.google.protobuf.Empty;
import proto.dtos.evento_solidario.CrearEventoSolidarioProto;
import proto.dtos.evento_solidario.EventoSolidarioProto;
import proto.dtos.evento_solidario.ModificarEventoSolidarioProto;
import proto.services.evento_solidario.ListarEventosSolidariosResponseProto;
import proto.services.evento_solidario.BooleanEventoSolidarioResponseProto;
import proto.services.evento_solidario.IdEventoSolidarioRequestProto;
import proto.services.evento_solidario.EventoSolidarioServiceGrpc;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IEventoSolidarioService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.EventoSolidarioMapper;

@GrpcService
@RequiredArgsConstructor
public class EventoSolidarioServiceGrpcImpl extends EventoSolidarioServiceGrpc.EventoSolidarioServiceImplBase {

    private final IEventoSolidarioService eventoSolidarioService;

    /// Obtengo todos los eventos solidarios
    @Override
    public void listarEventosSolidarios(Empty request, StreamObserver<ListarEventosSolidariosResponseProto> responseObserver) {

        try {
            List<EventoSolidarioDTO> eventosDTO = eventoSolidarioService.obtenerTodos();

            ListarEventosSolidariosResponseProto.Builder responseBuilder = ListarEventosSolidariosResponseProto.newBuilder();
            for (EventoSolidarioDTO eventoDTO : eventosDTO) {
                EventoSolidarioProto eventoProto = EventoSolidarioMapper.toProto(eventoDTO);
                responseBuilder.addEventos(eventoProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error al listar evento solidiario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    /// Agrego un evento solidario
    @Override
    public void crearEventoSolidario(CrearEventoSolidarioProto request, StreamObserver<EventoSolidarioProto> responseObserver) {

        try {
            CrearEventoSolidarioDTO dto = EventoSolidarioMapper.aCrearDTO(request);
            EventoSolidarioDTO creado = eventoSolidarioService.crearEventoSolidario(dto);
            EventoSolidarioProto response = EventoSolidarioMapper.toProto(creado);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al crear evento solidario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    /// Modifico un evento solidario
    @Override
    public void modificarEventoSolidario(ModificarEventoSolidarioProto request, StreamObserver<EventoSolidarioProto> responseObserver) {

        try {
            ModificarEventoSolidarioDTO dto = EventoSolidarioMapper.aModificarDTO(request);
            EventoSolidarioDTO modificado = eventoSolidarioService.modificarEventoSolidario(dto);
            EventoSolidarioProto response = EventoSolidarioMapper.toProto(modificado);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error inesperado: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    /// Elimino un evento solidario
    @Override
    public void eliminarEventoSolidario(IdEventoSolidarioRequestProto request, StreamObserver<BooleanEventoSolidarioResponseProto> responseObserver) {

        try {
            eventoSolidarioService.eliminarEventoSolidario(request.getIdEventoSolidario());
            BooleanEventoSolidarioResponseProto protoResponse = BooleanEventoSolidarioResponseProto.newBuilder()
                    .setResultado(true)
                    .build();

            responseObserver.onNext(protoResponse);
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription("Evento solidario no encontrado con id: " + request.getIdEventoSolidario())
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

    /// Participar de un evento solidario
    @Override
    public void participarDeEventoSolidario(IdEventoSolidarioRequestProto request, StreamObserver<EventoSolidarioProto> responseObserver) {
        try {
            EventoSolidarioDTO creado = eventoSolidarioService.participarDeEventoSolidario(request.getIdEventoSolidario());
            EventoSolidarioProto response = EventoSolidarioMapper.toProto(creado);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al querer participar del evento solidario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    /// Darse de baja de un evento solidario
    @Override
    public void darseDeBajaDeEventoSolidario(IdEventoSolidarioRequestProto request, StreamObserver<EventoSolidarioProto> responseObserver) {
        try {
            EventoSolidarioDTO creado = eventoSolidarioService.darseDeBajaDeEventoSolidario(request.getIdEventoSolidario());
            EventoSolidarioProto response = EventoSolidarioMapper.toProto(creado);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al querer darse de baja del evento solidario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    /// Traer evento solidario por ID
    @Override
    public void traerEventoSolidarioPorId(IdEventoSolidarioRequestProto request, StreamObserver<EventoSolidarioProto> responseObserver) {
        try {
            EventoSolidarioDTO creado = eventoSolidarioService.obtenerPorId(request.getIdEventoSolidario());
            EventoSolidarioProto response = EventoSolidarioMapper.toProto(creado);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al querer obtener el evento solidario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
