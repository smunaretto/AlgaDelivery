package com.algaworks.algadelivery.courier.management.infraestructure.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper.TypePrecedence;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.algaworks.algadelivery.courier.management.infraestructure.event.DeliveryFulfilledIntegrationEvent;
import com.algaworks.algadelivery.courier.management.infraestructure.event.DeliveryPlacedIntegrationEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
	private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;

    @Bean
    ConsumerFactory<String, Object> consumerFactory() {

        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>(objectMapper);

        jsonDeserializer.trustedPackages("com.algaworks.algadelivery.courier.management.infraestructure.event", "com.fasterxml.jackson.databind");

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(TypePrecedence.TYPE_ID);

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("delivery-placed-event", DeliveryPlacedIntegrationEvent.class);
        idClassMapping.put("delivery-pick-up-event", JsonNode.class);
        idClassMapping.put("delivery-fulfilled-event", DeliveryFulfilledIntegrationEvent.class);
        typeMapper.setIdClassMapping(idClassMapping);
        
        jsonDeserializer.setTypeMapper(typeMapper);


        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(jsonDeserializer)
        );
    }
    

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(ConsumerFactory<String, Object> consumerFactory) {
        // Este é o bean que a anotação @KafkaListener utiliza.
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
