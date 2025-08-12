package com.algaworks.algadelivery.devivery.tracking.domain.service;

import java.math.BigDecimal;

public interface CourierPayoutCalculationService {
	BigDecimal calculate(Double distanceInKm);
}
