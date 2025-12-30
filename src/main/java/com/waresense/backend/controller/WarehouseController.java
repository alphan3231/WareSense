package com.waresense.backend.controller;

import com.waresense.backend.dto.WarehouseDto;
import com.waresense.backend.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping("/zones")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<WarehouseDto.ZoneDto> createZone(@RequestBody WarehouseDto.ZoneDto dto) {
        return ResponseEntity.ok(warehouseService.createZone(dto));
    }

    @GetMapping("/zones")
    public ResponseEntity<List<WarehouseDto.ZoneDto>> getAllZones() {
        return ResponseEntity.ok(warehouseService.getAllZones());
    }

    @PostMapping("/shelves")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<WarehouseDto.ShelfDto> createShelf(@RequestBody WarehouseDto.ShelfDto dto) {
        return ResponseEntity.ok(warehouseService.createShelf(dto));
    }
}
