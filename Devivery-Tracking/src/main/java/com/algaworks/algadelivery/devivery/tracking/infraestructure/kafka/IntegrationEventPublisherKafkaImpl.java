package com.algaworks.algadelivery.devivery.tracking.infraestructure.kafka;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import com.algaworks.algadelivery.devivery.tracking.infraestructure.event.IntegrationEventPublisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class IntegrationEventPublisherKafkaImpl implements IntegrationEventPublisher {
	
	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void publish(Object event, String key, String topic) {
		SendResult<String, Object> result =  kafkaTemplate.send(topic, key, topic).join();
		log.info("Message publish: \n\t Topic: {}", result.getRecordMetadata().topic());
	}

}
