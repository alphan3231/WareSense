package com.waresense.backend.service;

import com.waresense.backend.dto.StockTransferDto;
import com.waresense.backend.entity.*;
import com.waresense.backend.exception.ResourceNotFoundException;
import com.waresense.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockTransferService {

    private final StockTransferRepository stockTransferRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository; // Assuming this exists or needed for logic
    private final ShelfRepository shelfRepository;
    private final UserRepository userRepository;

    public List<StockTransferDto> getAllTransfers() {
        return stockTransferRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public StockTransferDto createTransfer(StockTransferDto dto) {
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Shelf sourceShelf = shelfRepository.findById(dto.getSourceShelfId())
                .orElseThrow(() -> new ResourceNotFoundException("Source Shelf not found"));

        Shelf targetShelf = shelfRepository.findById(dto.getTargetShelfId())
                .orElseThrow(() -> new ResourceNotFoundException("Target Shelf not found"));

        User user = userRepository.findById(dto.getPerformedById())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Logic to update inventory would go here (decrease from source, increase in
        // target)
        // For now, we just record the transfer.

        StockTransfer transfer = StockTransfer.builder()
                .transferCode("TRF-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .product(product)
                .sourceShelf(sourceShelf)
                .targetShelf(targetShelf)
                .quantity(dto.getQuantity())
                .performedBy(user)
                .build();

        return mapToDto(stockTransferRepository.save(transfer));
    }

    private StockTransferDto mapToDto(StockTransfer transfer) {
        return StockTransferDto.builder()
                .id(transfer.getId())
                .transferCode(transfer.getTransferCode())
                .productId(transfer.getProduct().getId())
                .productName(transfer.getProduct().getName())
                .sourceShelfId(transfer.getSourceShelf().getId())
                .sourceShelfCode(transfer.getSourceShelf().getCode())
                .targetShelfId(transfer.getTargetShelf().getId())
                .targetShelfCode(transfer.getTargetShelf().getCode())
                .quantity(transfer.getQuantity())
                .transferDate(transfer.getTransferDate())
                .performedById(transfer.getPerformedBy().getId())
                .performedByUsername(transfer.getPerformedBy().getUsername())
                .build();
    }
}
