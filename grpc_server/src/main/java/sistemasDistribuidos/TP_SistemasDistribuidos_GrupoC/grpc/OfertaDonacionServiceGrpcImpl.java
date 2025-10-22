package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.dtos.oferta_donacion.OfertaDonacionProto;
import proto.services.kafka.PublicacionOfertaDonacionKafkaProto; // Asumido para ofertas externas
import proto.services.oferta_donacion.OfertaDonacionServiceGrpc;
import proto.services.oferta_donacion.OfertasDonacionesResponseProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.OfertaDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.OfertaDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IOfertaDonacionService;

@GrpcService
@RequiredArgsConstructor
public class OfertaDonacionServiceGrpcImpl extends OfertaDonacionServiceGrpc.OfertaDonacionServiceImplBase {
    ///Atributos:
    private final IOfertaDonacionService ofertaDonacionService;
    @Value("${ong.id}")
    private String ongEmpujeComunitarioId;

    ///Listar ofertas de donaciones de nuestra ONG:
    @Override
    public void listarTodasOfertasDonaciones(Empty request, StreamObserver<OfertasDonacionesResponseProto> responseObserver) {
    	try {
    		List<OfertaDonacionDTO> ofertas = ofertaDonacionService.listarOfertas();
    		
    		OfertasDonacionesResponseProto.Builder responseBuilder = OfertasDonacionesResponseProto.newBuilder();
    		for (OfertaDonacionDTO oferta : ofertas) {
    			OfertaDonacionProto ofertaProto = OfertaDonacionMapper.aProto(oferta);
    			responseBuilder.addOfertas(ofertaProto);
    		}
    		
    		responseObserver.onNext(responseBuilder.build());
    		responseObserver.onCompleted();
    		
    	} catch (Exception e) {
    		responseObserver.onError(
    				io.grpc.Status.INTERNAL
    				.withDescription("Error al listar ofertas de donaciones internas: " + e.getMessage())
    				.asRuntimeException()
    				);
    	}
    }
    
    ///Listar ofertas de donaciones propias de nuestra ONG:
    @Override
    public void listarOfertasDonacionesInternas(Empty request, StreamObserver<OfertasDonacionesResponseProto> responseObserver) {
        try {
            List<OfertaDonacionDTO> ofertas = ofertaDonacionService.listarOfertasPropias();

            OfertasDonacionesResponseProto.Builder responseBuilder = OfertasDonacionesResponseProto.newBuilder();
            for (OfertaDonacionDTO oferta : ofertas) {
                OfertaDonacionProto ofertaProto = OfertaDonacionMapper.aProto(oferta);
                responseBuilder.addOfertas(ofertaProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error al listar ofertas de donaciones internas: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
    
    ///Listar ofertas de donaciones de las demás ONGs:
    @Override
    public void listarOfertasDonacionesExternas(Empty request, StreamObserver<OfertasDonacionesResponseProto> responseObserver) {
        try {
            List<OfertaDonacionDTO> ofertas = ofertaDonacionService.listarOfertasExternas();

            OfertasDonacionesResponseProto.Builder responseBuilder = OfertasDonacionesResponseProto.newBuilder();
            for (OfertaDonacionDTO oferta : ofertas) {
                OfertaDonacionProto ofertaProto = OfertaDonacionMapper.aProto(oferta);
                responseBuilder.addOfertas(ofertaProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error al listar ofertas de donaciones externas: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }

    ///Crear oferta de donacion interna (Iniciada por nuestra ONG):
    @Override
    public void crearOfertaDonacionInterna(OfertaDonacionProto request, StreamObserver<Empty> responseObserver) {
        try {
            OfertaDonacionDTO dto = OfertaDonacionMapper.aDTO(request);
            ofertaDonacionService.crearOfertaDonacionInterna(dto); 
            
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }
    }
    
    ///Crear oferta de donacion externa (Recibida de Kafka):
    @Override
    public void crearOfertaDonacionExterna(PublicacionOfertaDonacionKafkaProto request, StreamObserver<Empty> responseObserver) {
        try {
            
            OfertaDonacionDTO dto = OfertaDonacionMapper.aDTO(request); 
            ofertaDonacionService.crearOfertaDonacionExterna(dto);
            
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
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