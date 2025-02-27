package com.example.grocerybookingapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

	private Long orderId;
	private BigDecimal totalAmount;
	private String shippingAddress;
	private String status;
	private LocalDateTime orderDate;
	private List<OrderItemResponse> orderItems;

	public OrderResponse(Long orderId, BigDecimal totalAmount, String shippingAddress, String status,
			LocalDateTime orderDate, List<OrderItemResponse> orderItems) {
		super();
		this.orderId = orderId;
		this.totalAmount = totalAmount;
		this.shippingAddress = shippingAddress;
		this.status = status;
		this.orderDate = orderDate;
		this.orderItems = orderItems;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public List<OrderItemResponse> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemResponse> orderItems) {
		this.orderItems = orderItems;
	}

	// Constructor, Getters, and Setters
}