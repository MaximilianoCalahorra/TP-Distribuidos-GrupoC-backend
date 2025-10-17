package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import net.devh.boot.grpc.server.service.GrpcService;
import lombok.RequiredArgsConstructor;
import com.google.protobuf.Empty;
import proto.dtos.inventario.CrearInventarioProto;
import proto.dtos.inventario.InventarioProto;
import proto.dtos.inventario.ModificarInventarioProto;
import proto.services.inventario.ListarInventariosResponseProto;
import proto.services.inventario.BooleanInventarioResponseProto;
import proto.services.inventario.IdInventarioRequestProto;
import proto.services.inventario.InventarioServiceGrpc;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations.InventarioService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.InventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.InventarioMapper;

@GrpcService
@RequiredArgsConstructor
public class InventarioServiceGrpcImpl extends InventarioServiceGrpc.InventarioServiceImplBase {
	///Atributo:
    private final InventarioService inventarioService;

	///Obtener todos los inventarios:
	@Override
	public void listarInventarios(Empty request, StreamObserver<ListarInventariosResponseProto> responseObserver) {
		try {
            List<InventarioDTO> inventarios = inventarioService.inventarios();

            ListarInventariosResponseProto.Builder responseBuilder = ListarInventariosResponseProto.newBuilder();
            for (InventarioDTO i : inventarios) {
                InventarioProto inventarioProto = InventarioMapper.aProto(i);
                responseBuilder.addInventarios(inventarioProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                io.grpc.Status.INTERNAL
                    .withDescription("Error al listar inventarios: " + e.getMessage())
                    .asRuntimeException()
            );
        }
	}
	
	///Obtener los inventarios activos:
	@Override
	public void listarInventariosActivos(Empty request, StreamObserver<ListarInventariosResponseProto> responseObserver) {
	    try {
	        List<InventarioDTO> inventariosActivos = inventarioService.inventariosActivos();

	        ListarInventariosResponseProto.Builder responseBuilder = ListarInventariosResponseProto.newBuilder();
	        for (InventarioDTO i : inventariosActivos) {
	            InventarioProto inventarioProto = InventarioMapper.aProto(i);
	            responseBuilder.addInventarios(inventarioProto);
	        }
	        
	        System.out.println(inventariosActivos);

	        responseObserver.onNext(responseBuilder.build());
	        responseObserver.onCompleted();
	    } catch (Exception e) {
	        responseObserver.onError(
	            io.grpc.Status.INTERNAL
	                .withDescription("Error al listar inventarios activos: " + e.getMessage())
	                .asRuntimeException()
	        );
	    }
	}
	
	///Agregar un inventario:
	@Override
	public void crearInventario(CrearInventarioProto request, StreamObserver<InventarioProto> responseObserver) {
        try {
            CrearInventarioDTO dto = InventarioMapper.aCrearInventarioDTO(request);
            InventarioDTO creado = inventarioService.crear(dto);
            InventarioProto response = InventarioMapper.aProto(creado);

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                io.grpc.Status.INVALID_ARGUMENT
                    .withDescription("Error al crear inventario: " + e.getMessage())
                    .asRuntimeException()
            );
        }
    }

	///Modificar un inventario:
    @Override
    public void modificarInventario(ModificarInventarioProto request, StreamObserver<InventarioProto> responseObserver) {
        try {
            ModificarInventarioDTO dto = InventarioMapper.aModificarInventarioDTO(request);
            InventarioDTO modificado = inventarioService.modificar(dto);
            InventarioProto response = InventarioMapper.aProto(modificado);

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

    ///Eliminar lógicamente un inventario:
    @Override
    public void eliminarLogicoInventario(IdInventarioRequestProto request, StreamObserver<BooleanInventarioResponseProto> responseObserver) {
        try {
            boolean resultado = inventarioService.eliminarLogico(request.getIdInventario());
            BooleanInventarioResponseProto response = BooleanInventarioResponseProto.newBuilder()
                    .setResultado(resultado)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                        .withDescription("Inventario no encontrado con id: " + request.getIdInventario())
                        .asRuntimeException()
                );
       }
    }
    
    ///Habilitar un inventario:
    @Override
    public void habilitarInventario(IdInventarioRequestProto request, StreamObserver<BooleanInventarioResponseProto> responseObserver) {
        try {
        	inventarioService.habilitarInventario(request.getIdInventario());
        	BooleanInventarioResponseProto response = BooleanInventarioResponseProto.newBuilder()
                    .setResultado(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                io.grpc.Status.NOT_FOUND
                    .withDescription("Inventario no encontrado con id: " + request.getIdInventario())
                    .asRuntimeException()
            );
        } catch (IllegalStateException e) {
            responseObserver.onError(
                io.grpc.Status.FAILED_PRECONDITION
                    .withDescription(e.getMessage())
                    .asRuntimeException()
            );
        } catch (Exception e) {
            responseObserver.onError(
                io.grpc.Status.INTERNAL
                    .withDescription("Error al habilitar inventario: " + e.getMessage())
                    .asRuntimeException()
            );
        }
    }

    @Override
    public void traerInventario(IdInventarioRequestProto request, StreamObserver<ModificarInventarioProto> responseObserver) {
        try {
            ModificarInventarioDTO modificarInventarioDTO = inventarioService.traerInventario(request.getIdInventario());
            ModificarInventarioProto responseProto = InventarioMapper.aModificarInventarioProto(modificarInventarioDTO);
            responseObserver.onNext(responseProto);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INVALID_ARGUMENT
                            .withDescription("Error al traer el inventario: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
}