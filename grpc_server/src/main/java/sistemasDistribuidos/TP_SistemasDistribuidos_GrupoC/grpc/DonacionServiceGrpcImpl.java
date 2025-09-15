package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import proto.dtos.donacion.CrearDonacionProto;
import proto.dtos.donacion.DonacionProto;
import proto.services.donacion.DonacionServiceGrpc;
import proto.services.donacion.IdEventoRequestProto;
import proto.services.donacion.ListarDonacionesResponseProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.DonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations.DonacionService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class DonacionServiceGrpcImpl extends DonacionServiceGrpc.DonacionServiceImplBase {

    private final DonacionService donacionService;

    @Override
    public void listarDonacionesPorEvento(IdEventoRequestProto request, StreamObserver<ListarDonacionesResponseProto> responseObserver) {
        try {
            List<DonacionDTO> donaciones = donacionService.traerDonacionesPorEvento(request.getIdEvento());

            ListarDonacionesResponseProto.Builder responseBuilder = ListarDonacionesResponseProto.newBuilder();
            for (DonacionDTO d : donaciones) {
                DonacionProto donacionProto = DonacionMapper.aProto(d);
                responseBuilder.addDonaciones(donacionProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error al listar donaciones: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void crearDonacion(CrearDonacionProto request, StreamObserver<DonacionProto> responseObserver) {
        try {
            CrearDonacionDTO dto = DonacionMapper.aCrearDonacionDTO(request);
            DonacionDTO creado = donacionService.crearDonacion(dto);
            DonacionProto response = DonacionMapper.aProto(creado);

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
                            .withDescription("Error al crear donación: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}