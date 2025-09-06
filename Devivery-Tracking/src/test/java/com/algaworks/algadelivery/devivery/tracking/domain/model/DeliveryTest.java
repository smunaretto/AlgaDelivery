package com.algaworks.algadelivery.devivery.tracking.domain.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.Test;

import com.algaworks.algadelivery.delivery.tracking.domain.exception.DomainException;
import com.algaworks.algadelivery.delivery.tracking.domain.model.ContactPoint;
import com.algaworks.algadelivery.delivery.tracking.domain.model.Delivery;
import com.algaworks.algadelivery.delivery.tracking.domain.model.Delivery.PreparationDetails;
import com.algaworks.algadelivery.delivery.tracking.domain.model.DeliveryStatus;

class DeliveryTest {

	@Test
	public void shouldChangeStatusgToPlaced() {
		Delivery delivery = Delivery.draft();
		
		delivery.editPreparationDetails(createdValidPrepationDetails());

		delivery.place();
		
		assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
		assertNotNull(delivery.getPlaceAt());
	}
	
	@Test
	public void shouldNotPlaced() {
		Delivery delivery = Delivery.draft();
		
		assertThrows(DomainException.class, () -> {
			delivery.place();
		});
		
		assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
		assertNull(delivery.getPlaceAt());
	}

	private PreparationDetails createdValidPrepationDetails() {
		
		ContactPoint sender = ContactPoint.builder()
				.zipCode("00000-000")
				.street("Rua A")
				.number("123")
				.complement("Sala 401")
				.name("João Silva")
				.phone("(11) 0200-0012")
				.build();
		
		
		ContactPoint recipient = ContactPoint.builder()
				.zipCode("12234-000")
				.street("Rua Rio de Janeiro")
				.number("500")
				.complement("")
				.name("Maria Silva")
				.phone("(11) 1223-12432")
				.build();
		
		return Delivery.PreparationDetails.builder()
				.sender(sender)
				.recipient(recipient)
				.distanceFee(new BigDecimal("15.00"))
				.courierPayout(new BigDecimal("5.00"))
				.expectedDelieryTime(Duration.ofHours(5))
				.build();
	}
	
}
