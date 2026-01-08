package com.waresense.backend.controller;

import com.waresense.backend.dto.StockTransferDto;
import com.waresense.backend.service.StockTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-transfers")
@RequiredArgsConstructor
@Tag(name = "Stock Transfers", description = "Operations for moving stock between shelves")
public class StockTransferController {

    private final StockTransferService stockTransferService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Get all stock transfers")
    public ResponseEntity<List<StockTransferDto>> getAllTransfers() {
        return ResponseEntity.ok(stockTransferService.getAllTransfers());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @Operation(summary = "Create a new stock transfer")
    public ResponseEntity<StockTransferDto> createTransfer(@RequestBody StockTransferDto dto) {
        return new ResponseEntity<>(stockTransferService.createTransfer(dto), HttpStatus.CREATED);
    }
}
