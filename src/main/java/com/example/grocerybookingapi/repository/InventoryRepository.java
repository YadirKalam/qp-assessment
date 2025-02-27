package com.example.grocerybookingapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.grocerybookingapi.model.GroceryItem;
import com.example.grocerybookingapi.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	Optional<Inventory> findByGroceryItemId(Long itemId);

	void deleteByGroceryItemId(Long itemId);

}
