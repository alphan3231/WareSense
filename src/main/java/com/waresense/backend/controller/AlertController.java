package com.waresense.backend.controller;

import com.waresense.backend.entity.StockAlert;
import com.waresense.backend.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {
    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<List<StockAlert>> getUnresolvedAlerts() {
        return ResponseEntity.ok(alertService.getUnresolvedAlerts());
    }

    @PostMapping("/{id}/resolve")
    public ResponseEntity<Void> resolveAlert(@PathVariable Long id) {
        alertService.resolveAlert(id);
        return ResponseEntity.noContent().build();
    }
}
