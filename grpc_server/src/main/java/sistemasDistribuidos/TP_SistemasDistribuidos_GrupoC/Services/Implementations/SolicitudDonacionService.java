package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import proto.services.kafka.PublicacionSolicitudDonacionKafkaProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Clients.KafkaServiceClient;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.BajaSolicitudDonacionDTO; 
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ItemDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.SolicitudDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Operacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.ItemDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.SolicitudDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.SolicitudDonacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.ISolicitudDonacionRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.ISolicitudDonacionService;

@Service
@RequiredArgsConstructor
public class SolicitudDonacionService implements ISolicitudDonacionService{
	///Atributos:
	private final ISolicitudDonacionRepository solicitudDonacionRepository;
    private final KafkaServiceClient kafkaServiceClient;
	@Value("${ong.id}")
    private String ongEmpujeComunitarioId;
	
	///Solicitudes de una organizacion:
	@Override
	public List<SolicitudDonacionDTO> solicitudesDonacionDeOrganizacion(String idOrganizacion) {
		List<SolicitudDonacion> solicitudes = solicitudDonacionRepository.findByIdOrganizacion(ongEmpujeComunitarioId);
        return solicitudes.stream()
                         .map(SolicitudDonacionMapper::aDTO) 
                         .collect(Collectors.toList());
	}
	
	///Solicitudes de todas las organizaciones menos una:
	@Override
	public List<SolicitudDonacionDTO> solicitudesDonacionDeDemasOrganizaciones(String idOrganizacion) {
		List<SolicitudDonacion> solicitudes = solicitudDonacionRepository.findByIdOrganizacionNot(ongEmpujeComunitarioId);
        return solicitudes.stream()
                         .map(SolicitudDonacionMapper::aDTO) 
                         .collect(Collectors.toList());
	}
	
	///Crear solicitud de donacion interna:
	@Override
	@Transactional
	public void crearSolicitudDonacionInterna(SolicitudDonacionDTO solicitud) {
		//Cargamos el tipo de operación en cada ítem:
        for (ItemDonacionDTO item: solicitud.getItems()) {
        	item.setOperacion(Operacion.SOLICITUD);
        }
        
        //Cargamos el id de la organización:
        solicitud.setIdOrganizacion(ongEmpujeComunitarioId);
        
        //Persistimos la solicitud y la recuperamos:
        SolicitudDonacion solicitudEntidad = SolicitudDonacionMapper.aEntidad(solicitud);
        SolicitudDonacion solicitudGuardada = solicitudDonacionRepository.save(solicitudEntidad);
        
        //Sobre la entidad que acabamos de persistir, obtenemos el id autoincremental que le asignó para cargarlo como el id de origen de la solicitud y persistimos la carga:
        String idSolicitudDonacionOrigenGenerado = String.valueOf(solicitudGuardada.getIdSolicitudDonacion());
        solicitudGuardada.setIdSolicitudDonacionOrigen(idSolicitudDonacionOrigenGenerado);
        solicitudDonacionRepository.save(solicitudGuardada);
        
        //Intentar publicar en Kafka el evento:
        try {
        	//Armar primera parte del mensaje:
            PublicacionSolicitudDonacionKafkaProto.Builder protoBuilder =
                PublicacionSolicitudDonacionKafkaProto.newBuilder()
                    .setIdOrganizacion(ongEmpujeComunitarioId)
                    .setIdSolicitud(String.valueOf(solicitudGuardada.getIdSolicitudDonacion()));

            //Agregar ítems al mensaje:
            for (ItemDonacionDTO item : solicitud.getItems()) {
                protoBuilder.addDonaciones(ItemDonacionMapper.aItemSolicitudProto(item));
            }

            //Construir el mensaje final:
            PublicacionSolicitudDonacionKafkaProto proto = protoBuilder.build();

            //Publicar:
            kafkaServiceClient.publicarSolicitudDonacion(proto);
        } catch (Exception e) {
            System.out.println("Error publicando la solicitud de donación en Kafka " + e);
        }
	}
	
	///Crear solicitud de donacion externa:
	public void crearSolicitudDonacionExterna(SolicitudDonacionDTO solicitud) {
		//Validar que no exista la solicitud:
		Optional<SolicitudDonacion> existente = solicitudDonacionRepository.findByIdSolicitudDonacionOrigenAndIdOrganizacion(solicitud.getIdSolicitudDonacionOrigen(),
																															 solicitud.getIdOrganizacion());
		
		//Si ya existe...
		if (existente.isPresent()) {
		    throw new EntityExistsException("La solicitud externa ya existe."); //Mensaje informativo.
		}
		
		//Si no existe, cargamos el tipo de operación en cada ítem:
        for (ItemDonacionDTO item: solicitud.getItems()) {
        	item.setOperacion(Operacion.SOLICITUD);
        }
        
        //Persistimos la solicitud:
        SolicitudDonacion solicitudEntidad = SolicitudDonacionMapper.aEntidad(solicitud);
        solicitudDonacionRepository.save(solicitudEntidad);
	}

    @Override
    @Transactional
    public void procesarBajaSolicitud(String idOrganizacion, String idSolicitud) {
        
        // Buscar la Solicitud en la base de datos local
        Optional<SolicitudDonacion> solicitudOpt = 
                solicitudDonacionRepository.findByIdSolicitudDonacionOrigenAndIdOrganizacion(
                        idSolicitud, 
                        idOrganizacion);

        // Si la solicitud existe, se procede al borrado físico.
        if (solicitudOpt.isPresent()) {
            
            SolicitudDonacion solicitud = solicitudOpt.get();
            
            // Borrar la entidad de la base de datos.
            solicitudDonacionRepository.delete(solicitud);
            
            System.out.println("Solicitud de donación borrada físicamente. IDs: " 
                               + idSolicitud + ", " + idOrganizacion);
        } else {
        
            System.out.println("Advertencia: Solicitud de donación no encontrada para borrado. IDs: " 
                               + idSolicitud + ", " + idOrganizacion);
        }
    }
}
