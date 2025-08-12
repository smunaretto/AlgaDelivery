package com.algaworks.algadelivery.devivery.tracking.infraestructure.http.client;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.algaworks.algadelivery.devivery.tracking.domain.service.CourierPayoutCalculationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

	private final CourierAPIClient courierAPIClient;
	
	@Override
	public BigDecimal calculate(Double distanceInKm) {
		
		var courierPayoutResultModel = courierAPIClient.payoutCalculation(
				new CourierPayoutCalculationInput(distanceInKm)
		);
		
		return courierPayoutResultModel.getPayoutFee();
	}

}
