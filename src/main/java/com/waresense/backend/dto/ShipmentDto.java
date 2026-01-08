package com.waresense.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShipmentDto {
    private Long id;
    private String trackingNumber;
    private Long orderId;
    private String orderCode;
    private String carrier;
    private String status;
    private LocalDateTime shipmentDate;
    private LocalDateTime estimatedArrival;
}
