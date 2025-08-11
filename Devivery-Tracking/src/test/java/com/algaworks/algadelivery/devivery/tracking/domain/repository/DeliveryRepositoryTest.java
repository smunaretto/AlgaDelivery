package com.algaworks.algadelivery.devivery.tracking.domain.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.algaworks.algadelivery.devivery.tracking.domain.model.ContactPoint;
import com.algaworks.algadelivery.devivery.tracking.domain.model.Delivery;
import com.algaworks.algadelivery.devivery.tracking.domain.model.Delivery.PreparationDetails;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DeliveryRepositoryTest {
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Test
	public void shoudPersist() {
		Delivery delivery = Delivery.draft();
		
		delivery.editPreparationDetails(createdValidPrepationDetails());
		
		delivery.addItem("Computador", 2);
		delivery.addItem("Notbook", 2);
		
		deliveryRepository.saveAndFlush(delivery);
		
		Delivery persistedDelivery = deliveryRepository.findById(delivery.getId()).orElseThrow();
		
		
		assertEquals(2, persistedDelivery.getItems().size());
		
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
