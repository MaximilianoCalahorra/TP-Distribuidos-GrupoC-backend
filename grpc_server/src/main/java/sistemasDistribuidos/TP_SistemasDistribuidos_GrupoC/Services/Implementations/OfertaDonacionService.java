package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import lombok.RequiredArgsConstructor;
import proto.services.kafka.PublicacionOfertaDonacionKafkaProto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Clients.KafkaServiceClient;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ItemDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.OfertaDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Operacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.ItemDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.OfertaDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IOfertaDonacionRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IOfertaDonacionService;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.OfertaDonacion;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfertaDonacionService implements IOfertaDonacionService {

    @Value("${ong.id}")
    private String ongEmpujeComunitarioId; ///cambiar esto por lo de arriba
    private final IOfertaDonacionRepository ofertaDonacionRepository;
    private final KafkaServiceClient kafkaServiceClient;
    
    /// Creo oferta interna
    @Override
    @Transactional
    public void crearOfertaDonacionInterna(OfertaDonacionDTO ofertaDTO) {
    	//Cargamos el tipo de operación en cada ítem:
        for (ItemDonacionDTO item: ofertaDTO.getItems()) {
        	item.setOperacion(Operacion.OFERTA);
        }
    	
        // Asigno el ID de nuestra organización
        // La ofertaDTO ya debe venir con los ItemDonacionDTO.
        ofertaDTO.setIdOrganizacion(ongEmpujeComunitarioId); // ID de nuestra ONG ("1")
        
        // Convierto DTO a Entidad
        OfertaDonacion nuevaOferta = OfertaDonacionMapper.aEntidad(ofertaDTO);
        
        // Base de datos local
        OfertaDonacion ofertaGuardada = ofertaDonacionRepository.save(nuevaOferta);
        
        //Sobre la entidad que acabamos de persistir, obtenemos el id autoincremental que le asignó para cargarlo como el id de origen de la oferta y persistimos la carga:
        String idOfertaDonacionOrigenGenerado = String.valueOf(ofertaGuardada.getIdOfertaDonacion());
        ofertaGuardada.setIdOfertaDonacionOrigen(idOfertaDonacionOrigenGenerado);
        ofertaDonacionRepository.save(ofertaGuardada);
        
        //Intentar publicar en Kafka la oferta:
        try {
        	//Armar primera parte del mensaje:
        	PublicacionOfertaDonacionKafkaProto.Builder protoBuilder =
        			PublicacionOfertaDonacionKafkaProto.newBuilder()
                    .setIdOrganizacion(ongEmpujeComunitarioId)
                    .setIdOferta(String.valueOf(ofertaGuardada.getIdOfertaDonacion()));

            //Agregar ítems al mensaje:
            for (ItemDonacionDTO item : ofertaDTO.getItems()) {
                protoBuilder.addDonaciones(ItemDonacionMapper.aItemTransferenciaProto(item));
            }

            //Construir el mensaje final:
            PublicacionOfertaDonacionKafkaProto proto = protoBuilder.build();

            //Publicar:
            kafkaServiceClient.publicarOfertaDonacion(proto);
        } catch (Exception e) {
            System.out.println("Error publicando la oferta de donación en Kafka " + e);
        }
    }
    
    /// Creo oferta externa
    @Override
    @Transactional
    public void crearOfertaDonacionExterna(OfertaDonacionDTO ofertaDTO) {
    	//Validar que no exista la oferta:
		Optional<OfertaDonacion> existente = ofertaDonacionRepository.findByIdOfertaDonacionOrigenAndIdOrganizacion(ofertaDTO.getIdOfertaDonacionOrigen(),
																													ofertaDTO.getIdOrganizacion());
		
		//Si ya existe...
		if (existente.isPresent()) {
		    throw new EntityExistsException("La oferta externa ya existe."); //Mensaje informativo.
		}
		
		//Si no existe, cargamos el tipo de operación en cada ítem:
        for (ItemDonacionDTO item: ofertaDTO.getItems()) {
        	item.setOperacion(Operacion.OFERTA);
        }
        
        //Persistimos la oferta:
        OfertaDonacion ofertaEntidad = OfertaDonacionMapper.aEntidad(ofertaDTO);
        ofertaDonacionRepository.save(ofertaEntidad);
    }

    /// Listo ofertas de la ONG
    @Override
    public List<OfertaDonacionDTO> listarOfertas() {
    	// Usamos findByIdOrganizacion para buscar solo por nuestra ID
    	return ofertaDonacionRepository.findAll().stream()
    			.map(oferta -> OfertaDonacionMapper.aDTO(oferta))
    			.collect(Collectors.toList());
    }
    
    /// Listo ofertas propias
    @Override
    public List<OfertaDonacionDTO> listarOfertasPropias() {
        // Usamos findByIdOrganizacion para buscar solo por nuestra ID
        return ofertaDonacionRepository.findByIdOrganizacion(ongEmpujeComunitarioId).stream()
                .map(oferta -> OfertaDonacionMapper.aDTO(oferta))
                .collect(Collectors.toList());
    }

    /// Listo ofertas de las demas organizciones
    @Override
    public List<OfertaDonacionDTO> listarOfertasExternas() {
        // Usamos findByIdOrganizacionIsNot para buscar ofertas cuya ID de organización NO sea la nuestra
        return ofertaDonacionRepository.findByIdOrganizacionIsNot(ongEmpujeComunitarioId).stream()
                .map(oferta -> OfertaDonacionMapper.aDTO(oferta)) 
                .collect(Collectors.toList());
    } 

    /// busco una oferta de una organizacion especifica
    @Override
    public OfertaDonacionDTO buscarOfertaPorIdOrigenYOrganizacion(String idOferta, String idOrganizacion) {
        return ofertaDonacionRepository.findByIdOfertaDonacionOrigenAndIdOrganizacion(idOferta, idOrganizacion)
                .map(oferta -> OfertaDonacionMapper.aDTO(oferta)) 
                .orElse(null);  // Retorna null si no se encuentra
    }
}