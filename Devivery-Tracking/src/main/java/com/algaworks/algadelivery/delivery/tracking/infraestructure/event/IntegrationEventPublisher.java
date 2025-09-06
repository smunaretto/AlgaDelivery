package com.algaworks.algadelivery.delivery.tracking.infraestructure.event;

public interface IntegrationEventPublisher {
	void publish(Object event, String key, String topic);
}
