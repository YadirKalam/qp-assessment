package com.example.grocerybookingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocerybookingapi.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
