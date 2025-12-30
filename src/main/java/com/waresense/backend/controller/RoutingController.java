package com.waresense.backend.controller;

import com.waresense.backend.entity.Shelf;
import com.waresense.backend.service.RoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RoutingController {

    private final RoutingService routingService;

    @PostMapping("/calculate")
    public ResponseEntity<List<String>> calculateOptimalRoute(@RequestBody List<Long> shelfIds) {
        List<Shelf> route = routingService.calculateOptimalRoute(shelfIds);
        List<String> routeCodes = route.stream()
                .map(Shelf::getCode)
                .collect(Collectors.toList());
        return ResponseEntity.ok(routeCodes);
    }
}
