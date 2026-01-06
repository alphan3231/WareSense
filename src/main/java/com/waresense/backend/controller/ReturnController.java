package com.waresense.backend.controller;

import com.waresense.backend.entity.ReturnRequest;
import com.waresense.backend.service.ReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
@RequiredArgsConstructor
public class ReturnController {
    private final ReturnService returnService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ReturnRequest> createReturnRequest(
            @RequestParam Long productId,
            @RequestParam Integer quantity,
            @RequestParam String reason) {
        return ResponseEntity.ok(returnService.createReturnRequest(productId, quantity, reason));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Void> approveReturn(
            @PathVariable Long id,
            @RequestParam Long shelfId) {
        returnService.approveReturn(id, shelfId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<List<ReturnRequest>> getPendingReturns() {
        return ResponseEntity.ok(returnService.getPendingReturns());
    }
}
