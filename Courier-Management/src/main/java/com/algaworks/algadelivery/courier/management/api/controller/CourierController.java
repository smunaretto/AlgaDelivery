package com.algaworks.algadelivery.courier.management.api.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.algaworks.algadelivery.courier.management.api.model.CourierInput;
import com.algaworks.algadelivery.courier.management.api.model.CourierPayoutCalculationInput;
import com.algaworks.algadelivery.courier.management.api.model.CourierPayoutResultModel;
import com.algaworks.algadelivery.courier.management.domain.model.Courier;
import com.algaworks.algadelivery.courier.management.domain.repository.CourierRepository;
import com.algaworks.algadelivery.courier.management.domain.service.CourierPayoutService;
import com.algaworks.algadelivery.courier.management.domain.service.CourierRegistrationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
public class CourierController {

	private final CourierRegistrationService courierRegistrationService;
	private final CourierPayoutService courierPayoutService;
	private final CourierRepository courierRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Courier create(@Valid @RequestBody CourierInput input) {
		return courierRegistrationService.create(input);
	}
	
	@PutMapping("/{courierId}")
	public Courier update(@PathVariable UUID courierId,
			@Valid @RequestBody CourierInput input) {
		return courierRegistrationService.update(courierId, input);
	}
	
	
    @GetMapping
    public PagedModel<Courier> findAll(@PageableDefault Pageable pageable) {
        return new PagedModel<>(courierRepository.findAll(pageable));
    }

    @GetMapping("/{courierId}")
    public Courier findById(@PathVariable UUID courierId) {
        return courierRepository.findById(courierId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    
    
    @PostMapping("/payout-calculation")
    public CourierPayoutResultModel calculate(
            @RequestBody CourierPayoutCalculationInput input) {
        BigDecimal payoutFee = courierPayoutService.calculate(input.getDistanceInKm());
        return new CourierPayoutResultModel(payoutFee);
    }
	
}
