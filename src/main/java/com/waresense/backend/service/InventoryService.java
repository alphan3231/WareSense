package com.waresense.backend.service;

import com.waresense.backend.dto.InventoryDto;
import com.waresense.backend.entity.InventoryItem;
import com.waresense.backend.entity.Product;
import com.waresense.backend.entity.Shelf;
import com.waresense.backend.repository.InventoryItemRepository;
import com.waresense.backend.repository.ProductRepository;
import com.waresense.backend.repository.ShelfRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryItemRepository inventoryItemRepository;
    private final ProductRepository productRepository;
    private final ShelfRepository shelfRepository;

    @Transactional
    public InventoryDto addStock(Long productId, Long shelfId, Integer quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Shelf shelf = shelfRepository.findById(shelfId)
                .orElseThrow(() -> new RuntimeException("Shelf not found"));

        // Check shelf capacity logic
        int currentShelfUsage = inventoryItemRepository.findByShelfId(shelfId).stream()
                .mapToInt(InventoryItem::getQuantity)
                .sum();

        if (currentShelfUsage + quantity > shelf.getCapacityTier()) {
            throw new RuntimeException(
                    "Shelf capacity exceeded! Available: " + (shelf.getCapacityTier() - currentShelfUsage));
        }

        // Find existing item on this shelf or create new
        Optional<InventoryItem> existingItem = inventoryItemRepository.findByShelfId(shelfId).stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        InventoryItem inventoryItem;
        if (existingItem.isPresent()) {
            inventoryItem = existingItem.get();
            inventoryItem.setQuantity(inventoryItem.getQuantity() + quantity);
        } else {
            inventoryItem = InventoryItem.builder()
                    .product(product)
                    .shelf(shelf)
                    .quantity(quantity)
                    .build();
        }

        InventoryItem savedItem = inventoryItemRepository.save(inventoryItem);
        return mapToDto(savedItem);
    }

    private InventoryDto mapToDto(InventoryItem item) {
        return InventoryDto.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .productSku(item.getProduct().getSku())
                .shelfId(item.getShelf().getId())
                .shelfCode(item.getShelf().getCode())
                .quantity(item.getQuantity())
                .build();
    }

    @Transactional
    public void removeStock(Long productId, Integer quantity) {
        List<InventoryItem> items = inventoryItemRepository.findByProductId(productId);

        int totalAvailable = items.stream().mapToInt(InventoryItem::getQuantity).sum();
        if (totalAvailable < quantity) {
            throw new RuntimeException("Insufficient stock for Product ID " + productId + "! Available: "
                    + totalAvailable + ", Requested: " + quantity);
        }

        int remainingToRemove = quantity;
        for (InventoryItem item : items) {
            if (remainingToRemove <= 0)
                break;

            int itemQty = item.getQuantity();
            if (itemQty > 0) {
                if (itemQty >= remainingToRemove) {
                    item.setQuantity(itemQty - remainingToRemove);
                    remainingToRemove = 0;
                } else {
                    item.setQuantity(0);
                    remainingToRemove -= itemQty;
                }
                inventoryItemRepository.save(item);
            }
        }
    }
}
