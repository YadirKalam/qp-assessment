package com.example.grocerybookingapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.grocerybookingapi.dto.GroceryItemRequest;
import com.example.grocerybookingapi.dto.GroceryItemResponse;
import com.example.grocerybookingapi.exception.ResourceNotFoundException;
import com.example.grocerybookingapi.model.GroceryItem;
import com.example.grocerybookingapi.model.Inventory;
import com.example.grocerybookingapi.repository.GroceryItemRepository;
import com.example.grocerybookingapi.repository.InventoryRepository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GroceryItemService {

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    private static final Logger LOGGER = Logger.getLogger(GroceryItemService.class.getName());

    @Transactional
    public GroceryItemResponse addItem(GroceryItemRequest request) {
        try {
            GroceryItem groceryItem = new GroceryItem(request.getName(), request.getCategory(), request.getPrice(),
                    request.getDescription(), request.getCreatedByAdminId(), request.getImageUrl());

            groceryItem = groceryItemRepository.save(groceryItem);

            Inventory inventory = new Inventory(groceryItem, request.getQuantity());
            inventoryRepository.save(inventory);

            return mapToResponse(groceryItem, inventory.getQuantity());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding grocery item: " + e.getMessage(), e);
            throw new RuntimeException("Failed to add grocery item");
        }
    }

    @Transactional
    public GroceryItemResponse updateItem(Long itemId, GroceryItemRequest request) {
        try {
            GroceryItem groceryItem = groceryItemRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("GroceryItem not found with ID: " + itemId));

            groceryItem.setName(request.getName());
            groceryItem.setCategory(request.getCategory());
            groceryItem.setPrice(request.getPrice());
            groceryItem.setDescription(request.getDescription());
            groceryItem.setImageUrl(request.getImageUrl());

            groceryItem = groceryItemRepository.save(groceryItem);

            Inventory inventory = inventoryRepository.findByGroceryItemId(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for GroceryItem ID: " + itemId));

            return mapToResponse(groceryItem, inventory.getQuantity());
        } catch (ResourceNotFoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating grocery item: " + e.getMessage(), e);
            throw new RuntimeException("Failed to update grocery item");
        }
    }

    @Transactional
    public void deleteItem(Long itemId) {
        try {
            inventoryRepository.deleteByGroceryItemId(itemId); // First delete inventory
            groceryItemRepository.deleteById(itemId); // Then delete the grocery item
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting grocery item: " + e.getMessage(), e);
            throw new RuntimeException("Failed to delete grocery item");
        }
    }

    @Transactional
    public GroceryItemResponse updateInventory(Long itemId, int quantity) {
        try {
            Inventory inventory = inventoryRepository.findByGroceryItemId(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for GroceryItem ID: " + itemId));
            inventory.setQuantity(quantity);
            inventory = inventoryRepository.save(inventory);

            GroceryItem groceryItem = inventory.getGroceryItem();
            return mapToResponse(groceryItem, inventory.getQuantity());
        } catch (ResourceNotFoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating inventory: " + e.getMessage(), e);
            throw new RuntimeException("Failed to update inventory");
        }
    }

    public GroceryItemResponse getItemById(Long itemId) {
        try {
            GroceryItem groceryItem = groceryItemRepository.findById(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("GroceryItem not found with ID: " + itemId));

            Inventory inventory = inventoryRepository.findByGroceryItemId(itemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for GroceryItem ID: " + itemId));

            return mapToResponse(groceryItem, inventory.getQuantity());
        } catch (ResourceNotFoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching grocery item by ID: " + e.getMessage(), e);
            throw new RuntimeException("Failed to fetch grocery item");
        }
    }

    public Page<GroceryItemResponse> getAllItems(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<GroceryItem> itemsPage = groceryItemRepository.findAll(pageable);

            return itemsPage.map(item -> {
                Inventory inventory = inventoryRepository.findByGroceryItemId(item.getId())
                        .orElse(new Inventory(item, 0)); // default to zero if no inventory found
                return mapToResponse(item, inventory.getQuantity());
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching all grocery items: " + e.getMessage(), e);
            throw new RuntimeException("Failed to fetch grocery items");
        }
    }

    private GroceryItemResponse mapToResponse(GroceryItem groceryItem, int quantity) {
        GroceryItemResponse response = new GroceryItemResponse();
        response.setId(groceryItem.getId());
        response.setName(groceryItem.getName());
        response.setCategory(groceryItem.getCategory());
        response.setPrice(groceryItem.getPrice());
        response.setDescription(groceryItem.getDescription());
        response.setImageUrl(groceryItem.getImageUrl());
        response.setQuantity(quantity);
        return response;
    }
}
