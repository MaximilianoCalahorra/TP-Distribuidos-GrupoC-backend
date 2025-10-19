package tpSistemasDistribuidos.kafkaService.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;

import proto.services.kafka.PublicacionSolicitudDonacionKafkaProto;
import proto.services.kafka.PublicacionTransferenciaDonacionKafkaProto;
import tpSistemasDistribuidos.kafkaService.config.KafkaConfig;

@Component
public class KafkaProducerDonacion{
	///Atributos:
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaConfig kafkaConfig;
    
    @Autowired
    private ObjectMapper objectMapper;

    //Publicar solicitud de donación:
    public void publicarSolicitudDonacion(PublicacionSolicitudDonacionKafkaProto proto) {
    	try {
    	    //Convertir proto a JSON String:
    	    String jsonString = JsonFormat.printer()
    	            .omittingInsignificantWhitespace()
    	            .print(proto);

    	    //Parsear con Jackson a un objeto genérico:
    	    Object jsonObject = objectMapper.readValue(jsonString, Object.class);

    	    //Publicar el objeto:
    	    kafkaTemplate.send("solicitud-donaciones", jsonObject);

    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
    
    //Publicar transferencia de donación:
    public void publicarTransferenciaDonacion(PublicacionTransferenciaDonacionKafkaProto proto) {
    	try {
    		//Obtener idOrganizacionReceptora desde el proto:
            int idOrganizacionReceptora = Integer.parseInt(proto.getIdOrganizacionReceptora());

            //Construir el nombre del topic usando KafkaConfig:
            String topic = kafkaConfig.topicTransferencia(idOrganizacionReceptora);

            //Crear el topic dinámicamente si no existe:
            kafkaConfig.crearTopicDinamico(topic);
    		
    		//Convertir proto a JSON String:
    		String jsonString = JsonFormat.printer()
    				.omittingInsignificantWhitespace()
    				.print(proto);
    		
    		//Parsear con Jackson a un objeto genérico:
    		Object jsonObject = objectMapper.readValue(jsonString, Object.class);
    		
    		//Publicar el objeto:
    		kafkaTemplate.send(topic, jsonObject);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}