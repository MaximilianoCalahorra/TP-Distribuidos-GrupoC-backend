package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import lombok.RequiredArgsConstructor;
import proto.dtos.usuario.*;
import proto.services.usuario.*;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.*;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.UsuarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations.UsuarioService;
import java.util.List;

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
    public void login (LoginUsuarioProto request, StreamObserver<LoginUsuarioResponseProto> responseObserver) {
        try {
            LoginUsuarioDTO loginUsuarioDTO = UsuarioMapper.aLoginUsuarioDTO(request);
            LoginUsuarioResponseProto response = UsuarioMapper.aLoginUsuarioResponseProto(usuarioService.login(loginUsuarioDTO));
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

    @Override
    public void listarUsuarios(Empty request, StreamObserver<ListarUsuariosResponseProto> responseObserver) {
        try {
            List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();

            ListarUsuariosResponseProto.Builder responseBuilder = ListarUsuariosResponseProto.newBuilder();
            for (UsuarioDTO usuarioDTO : usuarios) {
                UsuarioProto usuarioProto = UsuarioMapper.aProto(usuarioDTO);
                responseBuilder.addUsuarios(usuarioProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al listar los usuarios: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void traerUsuario(IdUsuarioRequestProto request, StreamObserver<CrearUsuarioProto> responseObserver) {
        try {
            CrearUsuarioDTO crearUsuarioDTO = usuarioService.traerUsuario(request.getIdUsuario());
            CrearUsuarioProto responseProto = UsuarioMapper.aCrearUsuarioProto(crearUsuarioDTO);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al traer al usuario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void reactivarUsuario(IdUsuarioRequestProto request, StreamObserver<StringResponseProto> responseObserver) {
        try {
            String mensaje = usuarioService.reactivarUsuario(request.getIdUsuario());
            StringResponseProto responseProto = StringResponseProto.newBuilder().setMensaje(mensaje).build();
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al reactivar al usuario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    @Override
    public void traerUsuariosActivos(Empty request, StreamObserver<ListarUsuariosActivosResponseProto> responseObserver) {
        try {
            List<MiembroDTO> usuariosActivos = usuarioService.traerUsuariosActivos();

            ListarUsuariosActivosResponseProto.Builder responseBuilder = ListarUsuariosActivosResponseProto.newBuilder();
            for (MiembroDTO miembroDTO : usuariosActivos) {
                MiembroProto miembroProto = UsuarioMapper.aMiembroProto(miembroDTO);
                responseBuilder.addUsuariosActivos(miembroProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al listar los usuarios activos: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}

