package com.algaworks.algadelivery.devivery.tracking.domain.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algadelivery.devivery.tracking.domain.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

}
