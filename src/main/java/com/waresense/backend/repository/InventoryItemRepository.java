package com.waresense.backend.repository;

import com.waresense.backend.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    List<InventoryItem> findByProductId(Long productId);
    List<InventoryItem> findByShelfId(Long shelfId);
}
