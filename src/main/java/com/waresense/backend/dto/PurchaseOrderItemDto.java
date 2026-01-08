package com.waresense.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseOrderItemDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
}
