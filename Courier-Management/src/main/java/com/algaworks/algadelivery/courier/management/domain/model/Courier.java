package com.algaworks.algadelivery.courier.management.domain.model;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access =  AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PRIVATE)
public class Courier {
	
	@Id
	@EqualsAndHashCode.Include
	private UUID id;
	
	@Setter(value = AccessLevel.PUBLIC)
	private String name;
	
	@Setter(value = AccessLevel.PUBLIC)
	private String phone;

	
	private Integer fulfilledDeliveriesQuantity;
	
	private Integer pendingDeliveriesQuantity;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "courier")
	private List<AssignedDelivery> pendeingDeliveries = new ArrayList<>();
	
	private OffsetDateTime lastFulfilledDeliveryAt;
	
	public List<AssignedDelivery> getPendeingDeliveries(){
		return Collections.unmodifiableList(this.pendeingDeliveries);
	}
	
	
	
	public static Courier brandNew(String name, String phone) {
		Courier courier = new Courier();
		courier.setId(UUID.randomUUID());
		courier.setName(name);
		courier.setPhone(phone);
		courier.setPendingDeliveriesQuantity(0);
		courier.setFulfilledDeliveriesQuantity(0);
		return courier;
	}
	
	public void assign(UUID deliveryId) {
		this.pendeingDeliveries.add(
				AssignedDelivery.pending(deliveryId, this)
		);
		this.setPendingDeliveriesQuantity(
			this.getPendingDeliveriesQuantity() + 1
		);
	}
	
	public void fulfill(UUID deliveryId) {
		AssignedDelivery delivery = this.pendeingDeliveries.stream().filter(
				d -> d.getId().equals(deliveryId)
		).findFirst().orElseThrow();
		this.pendeingDeliveries.remove(delivery);
		
		this.pendingDeliveriesQuantity--;
		this.fulfilledDeliveriesQuantity++;
		this.lastFulfilledDeliveryAt = OffsetDateTime.now();
	}
	
	
}
