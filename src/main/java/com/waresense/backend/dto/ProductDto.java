package com.waresense.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private String categoryName;
    private Double volumePerUnit;
    private Integer minStockLevel;
}
