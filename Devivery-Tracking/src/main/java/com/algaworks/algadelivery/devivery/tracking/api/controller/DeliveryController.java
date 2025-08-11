package com.algaworks.algadelivery.devivery.tracking.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algadelivery.devivery.tracking.api.model.DeliveryInput;
import com.algaworks.algadelivery.devivery.tracking.domain.model.Delivery;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Delivery draft(@RequestBody @Valid DeliveryInput input) {
		
		return null;
	}
	
	
	@PutMapping("/{deliveryId}")
	public Delivery edit(@PathVariable UUID deliveryId,
			@RequestBody @Valid DeliveryInput input) {
		
		return null;
	}
}
