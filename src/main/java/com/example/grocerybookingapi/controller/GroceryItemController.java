package com.example.grocerybookingapi.controller;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.grocerybookingapi.dto.GroceryItemRequest;
import com.example.grocerybookingapi.dto.GroceryItemResponse;
import com.example.grocerybookingapi.service.GroceryItemService;
import com.example.grocerybookingapi.util.ResponseUtil;
import com.example.grocerybookingapi.util.ResponseWrapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/grocery-items")
public class GroceryItemController {

	@Autowired
	private GroceryItemService groceryItemService;
	


	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseWrapper<GroceryItemResponse>> addItem(
			@Valid @RequestBody GroceryItemRequest request) {
		try {
			GroceryItemResponse response = groceryItemService.addItem(request);
			return ResponseUtil.buildSuccessResponse(response, "Item added successfully");
		} catch (Exception e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to add item");
		}
	}

	@PutMapping("/{itemId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseWrapper<GroceryItemResponse>> updateItem(@PathVariable("itemId") Long itemId,
			@Valid @RequestBody GroceryItemRequest request) {
		try {
			GroceryItemResponse response = groceryItemService.updateItem(itemId, request);
			return ResponseUtil.buildSuccessResponse(response, "Item updated successfully");
		} catch (NoSuchElementException e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.NOT_FOUND, "Item not found with ID: " + itemId);
		} catch (Exception e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update item");
		}
	}

	
	@DeleteMapping("/{itemId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseWrapper<String>> deleteItem(@PathVariable("itemId") Long itemId) {
		try {
			groceryItemService.deleteItem(itemId);
			return ResponseUtil.buildSuccessResponse(null, "Item deleted successfully");
		} catch (NoSuchElementException e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.NOT_FOUND, "Item not found with ID: " + itemId);
		} catch (Exception e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete item");
		}
	}


	@PutMapping("/inventory/{itemId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseWrapper<GroceryItemResponse>> updateInventory(@PathVariable("itemId") Long itemId,
			@RequestParam(name = "quantity") int quantity) {
		try {
			GroceryItemResponse response = groceryItemService.updateInventory(itemId, quantity);
			return ResponseUtil.buildSuccessResponse(response, "Inventory updated successfully");
		} catch (NoSuchElementException e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.NOT_FOUND, "Item not found with ID: " + itemId);
		} catch (Exception e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update inventory");
		}
	}


	@GetMapping("/{itemId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<ResponseWrapper<GroceryItemResponse>> getItemById(@PathVariable("itemId") Long itemId) {
		try {
			GroceryItemResponse response = groceryItemService.getItemById(itemId);
			return ResponseUtil.buildSuccessResponse(response, "Item fetched successfully");
		} catch (NoSuchElementException e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.NOT_FOUND, "Item fetch failed");
		} catch (Exception e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"An unexpected error occurred while fetching item");
		}
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<ResponseWrapper<Page<GroceryItemResponse>>> getAllItems(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {
		try {
			Page<GroceryItemResponse> itemsPage = groceryItemService.getAllItems(page, size);
			return ResponseUtil.buildSuccessResponse(itemsPage, "Items fetched successfully");
		} catch (Exception e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch items");
		}
	}
}
