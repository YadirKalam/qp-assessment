package com.example.grocerybookingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocerybookingapi.model.GroceryItem;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long>{

}
