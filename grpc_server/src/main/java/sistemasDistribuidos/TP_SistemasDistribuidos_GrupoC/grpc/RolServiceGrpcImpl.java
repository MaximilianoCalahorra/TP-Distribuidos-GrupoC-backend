package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import io.grpc.stub.StreamObserver;
import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;
import lombok.RequiredArgsConstructor;
import com.google.protobuf.Empty;
import proto.dtos.rol.RolProto;
import proto.services.rol.ListarRolesResponseProto;
import proto.services.rol.RolServiceGrpc;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations.RolService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.RolMapper;

@GrpcService
@RequiredArgsConstructor
public class RolServiceGrpcImpl extends RolServiceGrpc.RolServiceImplBase {
	///Atributo:
    private final RolService rolService;

    @Override
    ///Obtener todos los roles:
    public void listarRoles(Empty request, StreamObserver<ListarRolesResponseProto> responseObserver) {
        try {
            List<Rol> roles = rolService.roles();

            ListarRolesResponseProto.Builder responseBuilder = ListarRolesResponseProto.newBuilder();
            for (Rol r : roles) {
                RolProto rolProto = RolMapper.aProto(r);
                responseBuilder.addRoles(rolProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                io.grpc.Status.INTERNAL
                    .withDescription("Error al listar roles: " + e.getMessage())
                    .asRuntimeException()
            );
        }
    }
}