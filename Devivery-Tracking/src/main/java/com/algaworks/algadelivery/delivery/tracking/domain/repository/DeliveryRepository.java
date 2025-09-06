package com.algaworks.algadelivery.delivery.tracking.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algadelivery.delivery.tracking.domain.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

}
