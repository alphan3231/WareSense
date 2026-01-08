package com.waresense.backend.dto;

import com.waresense.backend.entity.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PurchaseOrderDto {
    private Long id;
    private String poCode;
    private Long supplierId;
    private String supplierName;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime expectedDeliveryDate;
    private List<PurchaseOrderItemDto> items;
}
