package com.waresense.backend.service;

import com.waresense.backend.dto.CreateOrderRequest;
import com.waresense.backend.dto.OrderDto;
import com.waresense.backend.entity.*;
import com.waresense.backend.repository.OrderRepository;
import com.waresense.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    @Transactional
    public OrderDto createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .orderCode(UUID.randomUUID().toString())
                .customerName(request.getCustomerName())
                .status(OrderStatus.PENDING)
                .build();

        List<OrderItem> orderItems = request.getItems().stream().map(itemRequest -> {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));

            // Deduct Stock
            inventoryService.removeStock(product.getId(), itemRequest.getQuantity());

            return OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        return mapToDto(savedOrder);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        return mapToDto(order);
    }

    @Transactional
    public OrderDto updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
        order.setStatus(status);
        return mapToDto(orderRepository.save(order));
    }

    private OrderDto mapToDto(Order order) {
        List<OrderDto.OrderItemDto> items = order.getItems().stream()
                .map(item -> OrderDto.OrderItemDto.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .productSku(item.getProduct().getSku())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return OrderDto.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .status(order.getStatus())
                .customerName(order.getCustomerName())
                .createdAt(order.getCreatedAt())
                .createdBy(order.getCreatedBy() != null ? order.getCreatedBy().getUsername() : null)
                .assignedPicker(order.getAssignedPicker() != null ? order.getAssignedPicker().getUsername() : null)
                .items(items)
                .build();
    }
}
