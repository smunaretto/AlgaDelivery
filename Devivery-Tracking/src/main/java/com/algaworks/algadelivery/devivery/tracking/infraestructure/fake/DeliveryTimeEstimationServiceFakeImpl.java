package com.algaworks.algadelivery.devivery.tracking.infraestructure.fake;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.algaworks.algadelivery.devivery.tracking.domain.model.ContactPoint;
import com.algaworks.algadelivery.devivery.tracking.domain.service.DeliveryEstimate;
import com.algaworks.algadelivery.devivery.tracking.domain.service.DeliveryTimeEstimationService;


@Service
public class DeliveryTimeEstimationServiceFakeImpl implements DeliveryTimeEstimationService {

	@Override
	public DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver) {
		return new DeliveryEstimate(
				Duration.ofHours(3),
				3.1
		);
	}

}
