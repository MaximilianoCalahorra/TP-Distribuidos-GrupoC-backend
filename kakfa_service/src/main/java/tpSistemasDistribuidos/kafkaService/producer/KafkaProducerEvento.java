package tpSistemasDistribuidos.kafkaService.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;

import proto.services.kafka.AdhesionVoluntarioExternoRequestProto;
import proto.services.kafka.BajaEventoKafkaProto;
import tpSistemasDistribuidos.kafkaService.config.KafkaConfig;

@Component
public class KafkaProducerEvento {
	///Atributos:
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaConfig kafkaConfig;
    
    @Autowired
    private ObjectMapper objectMapper;

    //Publicar baja de un evento:
    public void publicarBajaEvento(BajaEventoKafkaProto proto) {
    	try {
    	    //Convertir proto a JSON String:
    	    String jsonString = JsonFormat.printer()
    	            .omittingInsignificantWhitespace()
    	            .print(proto);

    	    //Parsear con Jackson a un objeto genérico:
    	    Object jsonObject = objectMapper.readValue(jsonString, Object.class);

    	    //Publicar el objeto:
    	    kafkaTemplate.send("baja-evento-solidario", jsonObject);

    	} catch (Exception e) {
    	    e.printStackTrace();
    	}
    }
    
    //Publicar adhesión de participante interno a evento externo:
    public void publicarAdhesionParticipanteInterno(AdhesionVoluntarioExternoRequestProto proto) {
    	try {
            //Obtener idOrganizador desde el proto:
            int idOrganizador = Integer.parseInt(proto.getIdOrganizador());

            //Construir el nombre del topic usando KafkaConfig:
            String topic = kafkaConfig.topicAdhesion(idOrganizador);

            //Crear el topic dinámicamente si no existe:
            kafkaConfig.crearTopicDinamico(topic);

            //Convertir proto a JSON string:
            String jsonString = JsonFormat.printer()
                    .omittingInsignificantWhitespace()
                    .print(proto);

            //Parsear a objeto genérico
            Object jsonObject = objectMapper.readValue(jsonString, Object.class);

            //Publicar en Kafka:
            kafkaTemplate.send(topic, jsonObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
