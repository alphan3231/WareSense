package com.waresense.backend.controller;

import com.waresense.backend.dto.ShipmentDto;
import com.waresense.backend.service.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
@Tag(name = "Shipment Management", description = "Operations for tracking shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'COURIER')")
    @Operation(summary = "Get all shipments")
    public ResponseEntity<List<ShipmentDto>> getAllShipments() {
        return ResponseEntity.ok(shipmentService.getAllShipments());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'COURIER')")
    @Operation(summary = "Get shipment by ID")
    public ResponseEntity<ShipmentDto> getShipmentById(@PathVariable Long id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create a new shipment")
    public ResponseEntity<ShipmentDto> createShipment(@RequestBody ShipmentDto dto) {
        return new ResponseEntity<>(shipmentService.createShipment(dto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'COURIER')")
    @Operation(summary = "Update shipment status")
    public ResponseEntity<ShipmentDto> updateShipmentStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(shipmentService.updateShipmentStatus(id, status));
    }
}
