package com.algaworks.algadelivery.courier.management.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algadelivery.courier.management.domain.model.Courier;
import com.algaworks.algadelivery.courier.management.domain.repository.CourierRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CourierDeliveryService {
	
	private final CourierRepository courierRepository;
	
	public void assign(UUID deliveryId) {
		Courier courier = courierRepository.findTop1ByOrderByLastFulfilledDeliveryAtAsc()
                .orElse(null);

        if (courier == null) {
            log.warn("No courier available for assignment to delivery {}", deliveryId);
            return;
        }

        courier.assign(deliveryId);

        courierRepository.saveAndFlush(courier);

        log.info("Courier {} assigned to delivery {}", courier.getId(), deliveryId);
	}
	
	
	public void fulfill(UUID deliveryId) {
		Courier courier = courierRepository.findByPendingDeliveries_id(deliveryId)
                .orElse(null);

        if (courier == null) {
            log.warn("No courier found with pending delivery {}", deliveryId);
            return;
        }

        courier.fulfill(deliveryId);

        courierRepository.saveAndFlush(courier);

        log.info("Courier {} fulfilled the delivery {}", courier.getId(), deliveryId);
	}
}
