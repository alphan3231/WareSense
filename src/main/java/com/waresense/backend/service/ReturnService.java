package com.waresense.backend.service;

import com.waresense.backend.entity.ReturnRequest;
import com.waresense.backend.repository.ProductRepository;
import com.waresense.backend.repository.ReturnRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReturnService {
    private final ReturnRequestRepository returnRequestRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;
    private final AuditLogService auditLogService;

    public ReturnRequest createReturnRequest(Long productId, Integer quantity, String reason) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        ReturnRequest request = ReturnRequest.builder()
                .product(product)
                .quantity(quantity)
                .reason(reason)
                .requestDate(LocalDateTime.now())
                .status("PENDING")
                .build();

        return returnRequestRepository.save(request);
    }

    @Transactional
    public void approveReturn(Long requestId, Long shelfId) {
        ReturnRequest request = returnRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Return request not found"));

        if (!"PENDING".equals(request.getStatus())) {
            throw new RuntimeException("Request is not pending");
        }

        // Add stock back to inventory
        inventoryService.addStock(request.getProduct().getId(), shelfId, request.getQuantity());

        request.setStatus("APPROVED");
        returnRequestRepository.save(request);

        auditLogService.logAction("RETURN_APPROVED", "SYSTEM", "Return approved for request " + requestId);
    }

    public List<ReturnRequest> getPendingReturns() {
        return returnRequestRepository.findByStatus("PENDING");
    }
}
