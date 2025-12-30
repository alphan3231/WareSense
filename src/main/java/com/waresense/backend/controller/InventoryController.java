package com.waresense.backend.controller;

import com.waresense.backend.dto.InventoryDto;
import com.waresense.backend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<InventoryDto> addStock(
            @RequestParam Long productId,
            @RequestParam Long shelfId,
            @RequestParam Integer quantity
    ) {
        return ResponseEntity.ok(inventoryService.addStock(productId, shelfId, quantity));
    }
}
