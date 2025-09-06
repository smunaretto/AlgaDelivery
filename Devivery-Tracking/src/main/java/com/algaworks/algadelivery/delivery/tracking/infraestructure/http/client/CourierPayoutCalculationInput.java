package com.algaworks.algadelivery.delivery.tracking.infraestructure.http.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CourierPayoutCalculationInput {
    private Double distanceInKm;
}