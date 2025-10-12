package tpSistemasDistribuidos.kafkaService.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.util.JsonFormat;

import proto.services.kafka.AdhesionVoluntarioExternoRequestProto;
import proto.services.kafka.BajaEventoKafkaProto;
import tpSistemasDistribuidos.kafkaService.config.KafkaConfig;

public class KafkaProducerDonaciones {
	///Atributos:
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private KafkaConfig kafkaConfig;
    
    @Autowired
    private ObjectMapper objectMapper;

// Publicar oferta de donación a las demás ONGs:
    public void publicarOfertaDonacion(OfertaDonacionProto proto) {
        try {
            // Convertir proto a JSON String:
            String jsonString = JsonFormat.printer()
                    .omittingInsignificantWhitespace()
                    .print(proto);

            // Parsear con Jackson a un objeto genérico:
            Object jsonObject = objectMapper.readValue(jsonString, Object.class);

            //Publicar el objeto:
    	    kafkaTemplate.send("oferta-donaciones", jsonObject);

        } catch (Exception e) {
            
            e.printStackTrace();
        }
    }

    
}
