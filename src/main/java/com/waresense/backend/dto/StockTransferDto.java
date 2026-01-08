package com.waresense.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StockTransferDto {
    private Long id;
    private String transferCode;
    private Long productId;
    private String productName;
    private Long sourceShelfId;
    private String sourceShelfCode;
    private Long targetShelfId;
    private String targetShelfCode;
    private Integer quantity;
    private LocalDateTime transferDate;
    private Long performedById;
    private String performedByUsername;
}
