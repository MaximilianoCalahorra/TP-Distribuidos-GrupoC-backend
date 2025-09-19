package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import lombok.RequiredArgsConstructor;
import proto.dtos.usuario.CrearUsuarioProto;
import proto.dtos.usuario.LoginUsuarioProto;
import proto.dtos.usuario.ModificarUsuarioProto;
import proto.services.usuario.IdUsuarioRequestProto;
import proto.services.usuario.StringResponseProto;
import proto.services.usuario.UsuarioServiceGrpc;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearUsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.LoginUsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarUsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.UsuarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations.UsuarioService;

@GrpcService
@RequiredArgsConstructor
public class UsuarioServiceGrpcImpl extends UsuarioServiceGrpc.UsuarioServiceImplBase {

    private final UsuarioService usuarioService;

    @Override
    public void crearUsuario (CrearUsuarioProto request, StreamObserver<CrearUsuarioProto> responseObserver) {
        try {
            CrearUsuarioDTO crearUsuarioDTO = UsuarioMapper.aCrearUsuarioDTO(request);
            CrearUsuarioProto response = UsuarioMapper.aCrearUsuarioProto(usuarioService.crearUsuario(crearUsuarioDTO));
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al crear el usuario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void login (LoginUsuarioProto request, StreamObserver<LoginUsuarioProto> responseObserver) {
        try {
            LoginUsuarioDTO loginUsuarioDTO = UsuarioMapper.aLoginUsuarioDTO(request);
            LoginUsuarioProto response = UsuarioMapper.aLoginUsuarioProto(usuarioService.login(loginUsuarioDTO));
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al realizar el login: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void desactivarUsuario(IdUsuarioRequestProto request, StreamObserver<StringResponseProto> responseObserver) {
        try {
            String mensaje = usuarioService.desactivarUsuario(request.getIdUsuario());
            StringResponseProto responseProto = StringResponseProto.newBuilder().setMensaje(mensaje).build();
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al desactivar al usuario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void modificarUsuario(ModificarUsuarioProto request, StreamObserver<ModificarUsuarioProto> responseObserver) {
        try {
            ModificarUsuarioDTO modificarUsuarioDTO = UsuarioMapper.aModificarUsuarioDTO(request);
            ModificarUsuarioProto response = UsuarioMapper.aModificarUsuarioProto(usuarioService.modificarUsuario(modificarUsuarioDTO));
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al modificar el usuario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}

