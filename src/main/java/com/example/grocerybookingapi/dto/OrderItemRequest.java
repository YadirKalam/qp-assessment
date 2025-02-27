package com.example.grocerybookingapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class OrderItemRequest {

    @NotNull
    private Long groceryItemId;

    @NotNull
    @Positive
    private int quantity;

    @NotNull
    private BigDecimal price;

	public Long getGroceryItemId() {
		return groceryItemId;
	}

	public void setGroceryItemId(Long groceryItemId) {
		this.groceryItemId = groceryItemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
