package com.waresense.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class WarehouseDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ZoneDto {
        private Long id;
        private String name;
        private String code;
        private List<ShelfDto> shelves;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShelfDto {
        private Long id;
        private String code;
        private Long zoneId;
        private Integer capacityTier;
    }
}
