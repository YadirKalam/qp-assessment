package com.example.grocerybookingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocerybookingapi.model.Order;

public interface OrderRepository  extends JpaRepository<Order, Long> {

}
