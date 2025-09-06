package com.algaworks.algadelivery.delivery.tracking.domain.service;

import java.math.BigDecimal;

public interface CourierPayoutCalculationService {
	BigDecimal calculate(Double distanceInKm);
}
