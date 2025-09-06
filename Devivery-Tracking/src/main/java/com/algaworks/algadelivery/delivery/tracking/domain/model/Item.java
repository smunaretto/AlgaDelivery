package com.algaworks.algadelivery.delivery.tracking.domain.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter(value = AccessLevel.PRIVATE)
@Getter
public class Item {
	
	@Id
	@EqualsAndHashCode.Include
	private UUID id;
	private String name;
	@Setter(value = AccessLevel.PACKAGE)
	private Integer quantity;
	
	@ManyToOne(optional =  false)
	@Getter(value = AccessLevel.PRIVATE)
	private Delivery delivery;
	
	static Item brandNew(String name, Integer quantity, Delivery delivery) {
		Item item = new Item();
		item.setId(UUID.randomUUID());
		item.setName(name);
		item.setQuantity(quantity);
		item.setDelivery(delivery);
		return item;
	}
}
