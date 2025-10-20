package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.grpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.google.protobuf.Empty;

import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.dtos.solicitud_donacion.SolicitudDonacionProto;
import proto.services.solicitud_donacion.SolicitudesDonacionesResponseProto;
import proto.services.kafka.BajaSolicitudDonacionKafkaProto;
import proto.services.kafka.PublicacionSolicitudDonacionKafkaProto;
import proto.services.solicitud_donacion.BajaSolicitudDonacionRequestProto;
import proto.services.solicitud_donacion.SolicitudDonacionServiceGrpc;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.SolicitudDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.SolicitudDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.ISolicitudDonacionService;

@GrpcService
@RequiredArgsConstructor
public class SolicitudDonacionServiceGrpcImpl extends SolicitudDonacionServiceGrpc.SolicitudDonacionServiceImplBase {
	///Atributos:
	private final ISolicitudDonacionService solicitudDonacionService;
	@Value("${ong.id}")
    private String ongEmpujeComunitarioId;
	
	///Listar solicitudes de donaciones de nuestra ONG:
    @Override
    public void listarSolicitudesDonacionesInternas(Empty request, StreamObserver<SolicitudesDonacionesResponseProto> responseObserver) {

        try {
            List<SolicitudDonacionDTO> solicitudes = solicitudDonacionService.solicitudesDonacionDeOrganizacion(ongEmpujeComunitarioId);

            SolicitudesDonacionesResponseProto.Builder responseBuilder = SolicitudesDonacionesResponseProto.newBuilder();
            for (SolicitudDonacionDTO solicitud : solicitudes) {
                SolicitudDonacionProto solicitudProto = SolicitudDonacionMapper.aProto(solicitud);
                responseBuilder.addSolicitudes(solicitudProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error al listar solicitudes de donaciones internas: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
    
    ///Listar solicitudes de donaciones de las demás ONGs:
    @Override
    public void listarSolicitudesDonacionesExternas(Empty request, StreamObserver<SolicitudesDonacionesResponseProto> responseObserver) {

        try {
            List<SolicitudDonacionDTO> solicitudes = solicitudDonacionService.solicitudesDonacionDeDemasOrganizaciones(ongEmpujeComunitarioId);

            SolicitudesDonacionesResponseProto.Builder responseBuilder = SolicitudesDonacionesResponseProto.newBuilder();
            for (SolicitudDonacionDTO solicitud : solicitudes) {
                SolicitudDonacionProto solicitudProto = SolicitudDonacionMapper.aProto(solicitud);
                responseBuilder.addSolicitudes(solicitudProto);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription("Error al listar solicitudes de donaciones externas: " + e.getMessage())
                            .asRuntimeException()
            );
        }
    }
    
    ///Crear solicitud de donacion interna:
    @Override
    public void crearSolicitudDonacionInterna(SolicitudDonacionProto request, StreamObserver<Empty> responseObserver) {

    	try {
    		SolicitudDonacionDTO dto = SolicitudDonacionMapper.aDTO(request);
            solicitudDonacionService.crearSolicitudDonacionInterna(dto);
            
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
    
    ///Crear solicitud de donacion externa:
    @Override
    public void crearSolicitudDonacionExterna(PublicacionSolicitudDonacionKafkaProto request, StreamObserver<Empty> responseObserver) {

    	try {
    		SolicitudDonacionDTO dto = SolicitudDonacionMapper.aDTO(request);
            solicitudDonacionService.crearSolicitudDonacionExterna(dto);
            
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
    
    ///Procesar baja de solicitud de donación:
    @Override
    public void procesarBajaSolicitudDonacion(BajaSolicitudDonacionRequestProto request, StreamObserver<Empty> responseObserver) {
        try {
            solicitudDonacionService.procesarBajaSolicitud(ongEmpujeComunitarioId, request.getIdSolicitud());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription("Error al querer dar de baja la solicitud de donación: " + e.getMessage())
                            .asRuntimeException()
            );
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } 
    }
    
    ///Procesar baja de solicitud de donación externa:
    @Override
    public void procesarBajaSolicitudDonacionExterna(BajaSolicitudDonacionKafkaProto request, StreamObserver<Empty> responseObserver) {
        try {
            solicitudDonacionService.procesarBajaSolicitudExterna(request.getIdOrganizacion(), request.getIdSolicitud());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (EntityNotFoundException e) {
            responseObserver.onError(
                    io.grpc.Status.NOT_FOUND
                            .withDescription("Error al querer dar de baja la solicitud de donación: " + e.getMessage())
                            .asRuntimeException()
            );
        } catch (IllegalArgumentException e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } 
    }
}
