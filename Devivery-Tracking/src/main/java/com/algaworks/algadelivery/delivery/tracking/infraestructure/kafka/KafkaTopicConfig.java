package com.algaworks.algadelivery.delivery.tracking.infraestructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	
	public static final String deliveryEventsTopicName = "deliveries.v1.events";

	@Bean
	NewTopic deliveryEventsTopic() {
		return TopicBuilder.name(deliveryEventsTopicName)
				.partitions(3)
				.replicas(1)
				.build();
	}
	
}
