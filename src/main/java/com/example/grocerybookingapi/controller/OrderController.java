package com.example.grocerybookingapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.grocerybookingapi.dto.OrderRequest;
import com.example.grocerybookingapi.dto.OrderResponse;
import com.example.grocerybookingapi.service.OrderService;
import com.example.grocerybookingapi.util.ResponseUtil;
import com.example.grocerybookingapi.util.ResponseWrapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<ResponseWrapper<OrderResponse>> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
		try {
			OrderResponse response = orderService.createOrder(orderRequest);
			return ResponseUtil.buildSuccessResponse(response, "Order created successfully");
		} catch (Exception e) {
			return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create order");
		}
	}

//    @GetMapping("/{orderId}")
//    public ResponseEntity<ResponseWrapper<OrderResponse>> getOrderById(@PathVariable Long orderId) {
//        try {
//            OrderResponse response = orderService.getOrderById(orderId);
//            return ResponseUtil.buildSuccessResponse(response, "Order fetched successfully");
//        } catch (NoSuchElementException e) {
//            return ResponseUtil.buildFailureResponse(HttpStatus.NOT_FOUND, "Order not found with ID: " + orderId);
//        } catch (Exception e) {
//            return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred while fetching the order");
//        }
//    }
//
//    @PutMapping("/{orderId}")
//    public ResponseEntity<ResponseWrapper<OrderResponse>> updateOrder(@PathVariable Long orderId, @Valid @RequestBody OrderRequest orderRequest) {
//        try {
//            OrderResponse response = orderService.updateOrder(orderId, orderRequest);
//            return ResponseUtil.buildSuccessResponse(response, "Order updated successfully");
//        } catch (NoSuchElementException e) {
//            return ResponseUtil.buildFailureResponse(HttpStatus.NOT_FOUND, "Order not found with ID: " + orderId);
//        } catch (Exception e) {
//            return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update order");
//        }
//    }
//
//    @DeleteMapping("/{orderId}")
//    public ResponseEntity<ResponseWrapper<String>> deleteOrder(@PathVariable Long orderId) {
//        try {
//            orderService.deleteOrder(orderId);
//            return ResponseUtil.buildSuccessResponse(null, "Order deleted successfully");
//        } catch (NoSuchElementException e) {
//            return ResponseUtil.buildFailureResponse(HttpStatus.NOT_FOUND, "Order not found with ID: " + orderId);
//        } catch (Exception e) {
//            return ResponseUtil.buildFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete order");
//        }
//    }
}
