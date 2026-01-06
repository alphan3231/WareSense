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

    private final com.waresense.backend.service.ReportService reportService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<InventoryDto> addStock(
            @RequestParam Long productId,
            @RequestParam Long shelfId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(inventoryService.addStock(productId, shelfId, quantity));
    }

    @GetMapping("/report/csv")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> getInventoryCsv() {
        String csv = reportService.generateInventoryCsv();
        return ResponseEntity.ok()
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"inventory.csv\"")
                .body(csv);
    }
}
