package com.waresense.backend.dto;

import com.waresense.backend.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDto {
    private Long id;
    private String orderCode;
    private OrderStatus status;
    private String customerName;
    private LocalDateTime createdAt;
    private String createdBy; // Username
    private String assignedPicker; // Username
    private List<OrderItemDto> items;

    @Data
    @Builder
    public static class OrderItemDto {
        private Long productId;
        private String productName;
        private String productSku;
        private Integer quantity;
    }
}
