package com.algaworks.algadelivery.delivery.tracking.domain.event;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DeliveryPickUpEvent {

	private final OffsetDateTime occurendAt;
	private final UUID deliveryId;
}
