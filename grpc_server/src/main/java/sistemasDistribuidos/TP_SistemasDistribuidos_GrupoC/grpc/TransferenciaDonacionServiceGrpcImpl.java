package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.dtos.transferencia_donacion.TransferenciaDonacionProto;
import proto.services.transferencia_donacion.TransferenciasDonacionesResponseProto;
import proto.services.kafka.PublicacionTransferenciaDonacionKafkaProto;
import proto.services.transferencia_donacion.TransferenciaDonacionServiceGrpc;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.TransferenciaDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.TransferenciaDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.ITransferenciaDonacionService;

@GrpcService
@RequiredArgsConstructor
public class TransferenciaDonacionServiceGrpcImpl extends TransferenciaDonacionServiceGrpc.TransferenciaDonacionServiceImplBase {
	///Atributos:
	private final ITransferenciaDonacionService transferenciaDonacionService;
	@Value("${ong.id}")
    private String ongEmpujeComunitarioId;
	
	///Listar transferencias de nuestra ONG:
    @Override
    public void listarTransferenciasDonaciones(Empty request, StreamObserver<TransferenciasDonacionesResponseProto> responseObserver) {

        try {
            List<TransferenciaDonacionDTO> transferencias = transferenciaDonacionService.listarTransferencias();

            TransferenciasDonacionesResponseProto.Builder responseBuilder = TransferenciasDonacionesResponseProto.newBuilder();
            for (TransferenciaDonacionDTO transferencia : transferencias) {
                TransferenciaDonacionProto transferenciaProto = TransferenciaDonacionMapper.aProto(transferencia);
                responseBuilder.addTransferencias(transferenciaProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error al listar transferencias de donaciones: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
    
    ///Listar transferencias salientes de nuestra ONG:
    @Override
    public void listarTransferenciasDonacionesSalientes(Empty request, StreamObserver<TransferenciasDonacionesResponseProto> responseObserver) {

        try {
            List<TransferenciaDonacionDTO> transferencias = transferenciaDonacionService.listarTransferenciasSalientes(ongEmpujeComunitarioId);

            TransferenciasDonacionesResponseProto.Builder responseBuilder = TransferenciasDonacionesResponseProto.newBuilder();
            for (TransferenciaDonacionDTO transferencia : transferencias) {
                TransferenciaDonacionProto transferenciaProto = TransferenciaDonacionMapper.aProto(transferencia);
                responseBuilder.addTransferencias(transferenciaProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error al listar transferencias de donaciones salientes: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
    
    ///Listar transferencias entrantes de nuestra ONG:
    @Override
    public void listarTransferenciasDonacionesEntrantes(Empty request, StreamObserver<TransferenciasDonacionesResponseProto> responseObserver) {
    	
    	try {
    		List<TransferenciaDonacionDTO> transferencias = transferenciaDonacionService.listarTransferenciasEntrantes(ongEmpujeComunitarioId);
    		
    		TransferenciasDonacionesResponseProto.Builder responseBuilder = TransferenciasDonacionesResponseProto.newBuilder();
    		for (TransferenciaDonacionDTO transferencia : transferencias) {
    			TransferenciaDonacionProto transferenciaProto = TransferenciaDonacionMapper.aProto(transferencia);
    			responseBuilder.addTransferencias(transferenciaProto);
    		}
    		
    		responseObserver.onNext(responseBuilder.build());
    		responseObserver.onCompleted();
    		
    	} catch (Exception e) {
    		responseObserver.onError(
    				io.grpc.Status.INTERNAL
    				.withDescription("Error al listar transferencias de donaciones entrantes: " + e.getMessage())
    				.asRuntimeException()
    				);
    	}
    }
    
    ///Crear transferencia entrante:
    @Override
    public void crearTransferenciaEntrante(PublicacionTransferenciaDonacionKafkaProto request, StreamObserver<Empty> responseObserver) {

    	try {
    		TransferenciaDonacionDTO dto = TransferenciaDonacionMapper.aDTO(request);
    		transferenciaDonacionService.crearTransferenciaEntrante(dto);
            
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (EntityExistsException e) {
            responseObserver.onError(
                    io.grpc.Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
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
        } catch (IllegalStateException e) {
            responseObserver.onError(
                    io.grpc.Status.PERMISSION_DENIED
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }
    
    ///Crear transferencia saliente:
    @Override
    public void crearTransferenciaSaliente(TransferenciaDonacionProto request, StreamObserver<Empty> responseObserver) {

    	try {
    		TransferenciaDonacionDTO dto = TransferenciaDonacionMapper.aDTO(request);
    		transferenciaDonacionService.crearTransferenciaSaliente(dto);
            
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (EntityExistsException e) {
            responseObserver.onError(
                    io.grpc.Status.ALREADY_EXISTS
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
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
