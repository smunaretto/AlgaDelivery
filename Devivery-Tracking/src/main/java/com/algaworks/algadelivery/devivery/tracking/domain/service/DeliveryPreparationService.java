package com.algaworks.algadelivery.devivery.tracking.domain.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algadelivery.devivery.tracking.api.model.ContactPointInput;
import com.algaworks.algadelivery.devivery.tracking.api.model.DeliveryInput;
import com.algaworks.algadelivery.devivery.tracking.api.model.ItemIput;
import com.algaworks.algadelivery.devivery.tracking.domain.exception.DomainException;
import com.algaworks.algadelivery.devivery.tracking.domain.model.ContactPoint;
import com.algaworks.algadelivery.devivery.tracking.domain.model.Delivery;
import com.algaworks.algadelivery.devivery.tracking.domain.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

	private final DeliveryRepository deliveryRepository;
	
	private final DeliveryTimeEstimationService deliveryTimeEstimationService;
	private final CourierPayoutCalculationService courierPayoutCalculationService;

	@Transactional
	public Delivery draft(DeliveryInput input) {
		Delivery delivery = Delivery.draft();
		
		handlePreparation(input, delivery);
		
		return deliveryRepository.saveAndFlush(delivery);
	}
	
	
	@Transactional
	public Delivery edit(UUID deliveryId, DeliveryInput input) {
		Delivery delivery = deliveryRepository.findById(deliveryId)
				.orElseThrow(() -> new DomainException());
		
		delivery.removeItems();
		handlePreparation(input, delivery);
		
		return deliveryRepository.saveAndFlush(delivery);
	}

	private void handlePreparation(DeliveryInput input, Delivery delivery) {
		ContactPointInput senderInput = input.getSender();
		ContactPointInput recipientInput = input.getRecipient();
		
		ContactPoint sender = ContactPoint.builder()
				.phone(senderInput.getPhone())
				.name(senderInput.getName())
				.complement(senderInput.getComplement())
				.number(senderInput.getNumber())
				.zipCode(senderInput.getZipCode())
				.street(senderInput.getStreet())
				.build();
		ContactPoint recipient = ContactPoint.builder()
				.phone(recipientInput.getPhone())
				.name(recipientInput.getName())
				.complement(recipientInput.getComplement())
				.number(recipientInput.getNumber())
				.zipCode(recipientInput.getZipCode())
				.street(recipientInput.getStreet())
				.build();
		
		
		DeliveryEstimate estimate = deliveryTimeEstimationService.estimate(sender, recipient);
		BigDecimal calculatePayout = courierPayoutCalculationService.calculate(estimate.getDistanceInKm());	
		
		BigDecimal distanceFee = calculateFee(estimate.getDistanceInKm());
		
		var preparationDetails = Delivery.PreparationDetails.builder()
				.sender(sender)
				.recipient(recipient)
				.courierPayout(calculatePayout)
				.distanceFee(distanceFee)
				.expectedDelieryTime(estimate.getEstimatedTime())
				.build();
		
		delivery.editPreparationDetails(preparationDetails);
		
		for (ItemIput itemIput : input.getItems()) {
			delivery.addItem(itemIput.getName(), itemIput.getQuantity());
		}
	}


	private BigDecimal calculateFee(Double distanceInKm) {
		return new BigDecimal(3)
				.multiply(new BigDecimal(distanceInKm))
				.setScale(2, RoundingMode.HALF_EVEN);
	}
}
