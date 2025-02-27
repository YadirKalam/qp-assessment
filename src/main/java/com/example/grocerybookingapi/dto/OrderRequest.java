package com.example.grocerybookingapi.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    private String shippingAddress;

    @NotNull
    private List<OrderItemRequest> orderItems;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<OrderItemRequest> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemRequest> orderItems) {
		this.orderItems = orderItems;
	}

    // Getters and Setters
}
