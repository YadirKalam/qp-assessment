package com.example.grocerybookingapi.dto;

import java.math.BigDecimal;

public class OrderItemResponse {
	
	private Long groceryItemId;
	private String name;
	private int quantity;
	private BigDecimal price;


	public OrderItemResponse(Long groceryItemId, String name, int quantity, BigDecimal price) {
		super();
		this.groceryItemId = groceryItemId;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}

	public Long getGroceryItemId() {
		return groceryItemId;
	}

	public void setGroceryItemId(Long groceryItemId) {
		this.groceryItemId = groceryItemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
