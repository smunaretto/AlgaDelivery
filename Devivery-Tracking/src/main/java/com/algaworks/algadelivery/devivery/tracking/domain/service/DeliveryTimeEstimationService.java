package com.algaworks.algadelivery.devivery.tracking.domain.service;

import com.algaworks.algadelivery.devivery.tracking.domain.model.ContactPoint;

public interface DeliveryTimeEstimationService {

	DeliveryEstimate estimate(ContactPoint sender, ContactPoint receiver);
}
