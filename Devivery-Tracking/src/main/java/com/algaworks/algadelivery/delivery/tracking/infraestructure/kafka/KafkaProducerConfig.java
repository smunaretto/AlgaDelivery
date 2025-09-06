package com.algaworks.algadelivery.delivery.tracking.infraestructure.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper.TypePrecedence;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.algaworks.algadelivery.delivery.tracking.domain.event.DeliveryFulfilledEvent;
import com.algaworks.algadelivery.delivery.tracking.domain.event.DeliveryPickUpEvent;
import com.algaworks.algadelivery.delivery.tracking.domain.event.DeliveryPlacedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

	private final KafkaProperties kafkaProperties;
	private final ObjectMapper objectMapper;
	
	
	@Bean
    ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        JsonSerializer<Object> jsonSerializer = new JsonSerializer<>(objectMapper);
        
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();       
        typeMapper.setTypePrecedence(TypePrecedence.TYPE_ID);

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("delivery-placed-event", DeliveryPlacedEvent.class);
        idClassMapping.put("delivery-pick-up-event", DeliveryPickUpEvent.class);
        idClassMapping.put("delivery-fulfilled-event", DeliveryFulfilledEvent.class);
        typeMapper.setIdClassMapping(idClassMapping);
        
        jsonSerializer.setTypeMapper(typeMapper);

        return new DefaultKafkaProducerFactory<>(props, new StringSerializer(), jsonSerializer);
    }

	
    @Bean
    KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
