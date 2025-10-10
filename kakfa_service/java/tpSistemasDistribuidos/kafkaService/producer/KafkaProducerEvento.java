package tpSistemasDistribuidos.kakfaService.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;

import proto.services.kafka.BajaEventoKafkaProto;

@Component
public class KafkaProducerEvento {
	///Atributos:
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
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
}
