package com.algaworks.algadelivery.courier.management.infraestructure.kafka;

import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.algaworks.algadelivery.courier.management.domain.service.CourierDeliveryService;
import com.algaworks.algadelivery.courier.management.infraestructure.event.DeliveryFulfilledIntegrationEvent;
import com.algaworks.algadelivery.courier.management.infraestructure.event.DeliveryPlacedIntegrationEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@KafkaListener(topics = {
		"deliveries.v1.events"
	}, groupId = "courier-management"
)
@Slf4j
@RequiredArgsConstructor
public class KafkaDeliveryMessageHandler {
	
	private final CourierDeliveryService courierDeliveryService;

	@KafkaHandler(isDefault = true)
	public void defaultHandler(@Payload Object object) {
		log.info("Default Handler: {}", object);
	}
	
	@KafkaHandler
	public void handle(@Payload DeliveryPlacedIntegrationEvent event) {
		log.info("Reicived> {}", event);
		courierDeliveryService.assign(event.getDeliveryId());
	}
	
	@KafkaHandler
	public void handle(@Payload DeliveryFulfilledIntegrationEvent event) {
		log.info("Reicived> {}", event);
		courierDeliveryService.fulfill(event.getDeliveryId());
	}
	
}
