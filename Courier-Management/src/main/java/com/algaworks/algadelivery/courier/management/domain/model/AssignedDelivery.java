package com.algaworks.algadelivery.courier.management.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter(value =  AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AssignedDelivery {

	@Id
	@EqualsAndHashCode.Include
	private UUID id;
	
	@ManyToOne(optional =  false)
	@Getter(value = AccessLevel.PRIVATE)
	private Courier courier;
	
	private OffsetDateTime assifnedAt;
	
	static AssignedDelivery pending(UUID deliveryID, Courier courier) {
		AssignedDelivery delivery = new AssignedDelivery();
		delivery.setId(deliveryID);
		delivery.setAssifnedAt(OffsetDateTime.now());
		delivery.setCourier(courier);
		return delivery;
	}
}
