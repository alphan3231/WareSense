package com.waresense.backend.service;

import com.waresense.backend.entity.InventoryItem;
import com.waresense.backend.entity.Product;
import com.waresense.backend.entity.StockAlert;
import com.waresense.backend.repository.InventoryItemRepository;
import com.waresense.backend.repository.ProductRepository;
import com.waresense.backend.repository.StockAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {
    private final ProductRepository productRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final StockAlertRepository stockAlertRepository;

    @Scheduled(fixedRate = 60000) // Check every minute
    public void checkStockLevels() {
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            if (product.getMinStockLevel() != null) {
                int totalStock = inventoryItemRepository.findByProductId(product.getId()).stream()
                        .mapToInt(InventoryItem::getQuantity)
                        .sum();

                if (totalStock < product.getMinStockLevel()) {
                    createAlertIfNotExists(product, totalStock);
                }
            }
        }
    }

    private void createAlertIfNotExists(Product product, int currentStock) {
        // Simple logic: create alert if not already unresolved alert exists for this
        // product?
        // For simplicity, we just create one. In real app, avoid spamming.
        // Let's check if there is already an unresolved alert for this product.
        boolean hasUnresolved = stockAlertRepository.findByResolvedFalse().stream()
                .anyMatch(a -> a.getProduct().getId().equals(product.getId()));

        if (!hasUnresolved) {
            StockAlert alert = StockAlert.builder()
                    .product(product)
                    .currentStock(currentStock)
                    .minStockThreshold(product.getMinStockLevel())
                    .alertTime(LocalDateTime.now())
                    .resolved(false)
                    .build();
            stockAlertRepository.save(alert);
        }
    }

    public List<StockAlert> getUnresolvedAlerts() {
        return stockAlertRepository.findByResolvedFalse();
    }

    public void resolveAlert(Long id) {
        StockAlert alert = stockAlertRepository.findById(id).orElseThrow();
        alert.setResolved(true);
        stockAlertRepository.save(alert);
    }
}
