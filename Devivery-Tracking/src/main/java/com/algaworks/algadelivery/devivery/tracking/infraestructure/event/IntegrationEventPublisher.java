package com.algaworks.algadelivery.devivery.tracking.infraestructure.event;

public interface IntegrationEventPublisher {
	void publish(Object event, String key, String topic);
}
