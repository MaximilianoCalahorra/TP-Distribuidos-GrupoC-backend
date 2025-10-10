package tpSistemasDistribuidos.kakfaService.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {
	
	@Autowired
    private KafkaAdmin kafkaAdmin;
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	@Value("${spring.kafka.consumer.group-id}")
	private String consumerGroupId;

	//Fábrica de productores Kakfa:
    @Bean
    ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); //Conexión con el broker.
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); //Serialización de claves.
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class); //Serialización de valores.
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    //Wrapper para enviar mensajes:
    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    //Fábrica de consumidores Kafka:
    @Bean
    ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); //Configuración del broker.
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); //Deserializador de claves.
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); //Deserializador de valores.
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId); //ID del grupo de consumidores.
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(props);
    }

    //Contenedor para los listeners de Kafka (permite recibir mensajes concurrentemente):
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    //Topics fijos:
    @Bean
    NewTopic solicitudDonacionesTopic() {
        return new NewTopic("solicitud-donaciones", 1, (short) 1);
    }

    @Bean
    NewTopic ofertaDonacionesTopic() {
        return new NewTopic("oferta-donaciones", 1, (short) 1);
    }

    @Bean
    NewTopic bajaSolicitudDonacionesTopic() {
        return new NewTopic("baja-solicitud-donaciones", 1, (short) 1);
    }

    @Bean
    NewTopic eventosSolidariosTopic() {
        return new NewTopic("eventos-solidarios", 1, (short) 1);
    }

    @Bean
    NewTopic bajaEventoSolidarioTopic() {
        return new NewTopic("baja-evento-solidario", 1, (short) 1);
    }

    //Función para crear un topic dinámico:
    public void crearTopicDinamico(String nombreTopic) {
        NewTopic topic = new NewTopic(nombreTopic, 1, (short) 1); //Crea un topic con el nombre indicado.
        kafkaAdmin.createOrModifyTopics(topic); //KafkaAdmin gestiona ese topic.
    }
    
    //Helpers para construir los topics:
    
    //Transferencia de donaciones a una organización:
    public String topicTransferencia(int idOrganizacion) {
        return "transferencia-donaciones-" + idOrganizacion;
    }

    //Adhesión a un evento de una organización:
    public String topicAdhesion(int idOrganizador) {
        return "adhesion-evento-" + idOrganizador;
    }
}