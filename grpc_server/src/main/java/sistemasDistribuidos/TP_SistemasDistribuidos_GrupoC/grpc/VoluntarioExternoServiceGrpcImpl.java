package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.dtos.voluntario_externo.VoluntarioExternoProto;
import proto.services.voluntario_externo.VoluntarioExternoServiceGrpc;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.VoluntarioExternoDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.VoluntarioExternoMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations.VoluntarioExternoService;

@GrpcService
@RequiredArgsConstructor
public class VoluntarioExternoServiceGrpcImpl extends VoluntarioExternoServiceGrpc.VoluntarioExternoServiceImplBase {
	///Atributo:
    private final VoluntarioExternoService voluntarioExternoService;

    ///Crear voluntario externo:
    @Override
    public void crearVoluntarioExterno(VoluntarioExternoProto request, StreamObserver<VoluntarioExternoProto> responseObserver) {
        try {
            VoluntarioExternoDTO dto = VoluntarioExternoMapper.aDTO(request);
            VoluntarioExternoDTO guardado = voluntarioExternoService.crearVoluntarioExterno(dto);
            VoluntarioExternoProto respuesta = VoluntarioExternoMapper.aProto(guardado);

            responseObserver.onNext(respuesta);
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al crear voluntario externo: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
