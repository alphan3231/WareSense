package com.waresense.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto {
    private Long id;
    private Long productId;
    private String productName;
    private String productSku;
    private Long shelfId;
    private String shelfCode;
    private Integer quantity;
}
