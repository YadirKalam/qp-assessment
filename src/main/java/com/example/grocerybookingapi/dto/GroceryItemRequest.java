package com.example.grocerybookingapi.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class GroceryItemRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    @Positive
    private BigDecimal price;

    private String description;

    private String imageUrl;
    
    private int quantity;

    public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private Long createdByAdminId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getCreatedByAdminId() {
		return createdByAdminId;
	}

	public void setCreatedByAdminId(Long createdByAdminId) {
		this.createdByAdminId = createdByAdminId;
	}

  
}
