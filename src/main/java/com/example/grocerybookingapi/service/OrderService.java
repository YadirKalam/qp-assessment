package com.example.grocerybookingapi.service;

import com.example.grocerybookingapi.dto.OrderRequest;
import com.example.grocerybookingapi.dto.OrderResponse;
import com.example.grocerybookingapi.dto.OrderItemResponse;
import com.example.grocerybookingapi.exception.ResourceNotFoundException;
import com.example.grocerybookingapi.model.Order;
import com.example.grocerybookingapi.model.OrderItem;
import com.example.grocerybookingapi.model.User;
import com.example.grocerybookingapi.model.GroceryItem;
import com.example.grocerybookingapi.repository.OrderRepository;
import com.example.grocerybookingapi.repository.UserRepository;
import com.example.grocerybookingapi.repository.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        try {
            User user = userRepository.findById(orderRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + orderRequest.getUserId()));

            List<OrderItem> orderItems = orderRequest.getOrderItems().stream().map(itemRequest -> {
                GroceryItem groceryItem = groceryItemRepository.findById(itemRequest.getGroceryItemId())
                        .orElseThrow(() -> new ResourceNotFoundException("GroceryItem not found with ID: " + itemRequest.getGroceryItemId()));

                return new OrderItem(
                        null,
                        groceryItem,
                        itemRequest.getQuantity(),
                        itemRequest.getPrice()
                );
            }).collect(Collectors.toList());

            BigDecimal totalAmount = orderItems.stream()
                    .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            Order order = new Order(user, LocalDateTime.now(), totalAmount, orderItems, Order.OrderStatus.PENDING, orderRequest.getShippingAddress());

            order.setOrderItems(orderItems);
            orderItems.forEach(item -> item.setOrder(order));

            Order savedOrder = orderRepository.save(order);
            return mapToOrderResponse(savedOrder);
        } catch (ResourceNotFoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating order: " + e.getMessage(), e);
            throw new RuntimeException("Failed to create order");
        }
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> orderItems = order.getOrderItems().stream().map(item -> {
            return new OrderItemResponse(
                    item.getGroceryItem().getId(),
                    item.getGroceryItem().getName(),
                    item.getQuantity(),
                    item.getPrice()
            );
        }).collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getShippingAddress(),
                order.getStatus().name(),
                order.getOrderDate(),
                orderItems
        );
    }
}
