package com.waresense.backend.service;

import com.waresense.backend.dto.PurchaseOrderDto;
import com.waresense.backend.dto.PurchaseOrderItemDto;
import com.waresense.backend.entity.*;
import com.waresense.backend.exception.ResourceNotFoundException;
import com.waresense.backend.repository.ProductRepository;
import com.waresense.backend.repository.PurchaseOrderRepository;
import com.waresense.backend.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    public List<PurchaseOrderDto> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PurchaseOrderDto getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found"));
    }

    @Transactional
    public PurchaseOrderDto createPurchaseOrder(PurchaseOrderDto dto) {
        Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        PurchaseOrder po = PurchaseOrder.builder()
                .poCode("PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .supplier(supplier)
                .status(OrderStatus.PENDING)
                .expectedDeliveryDate(dto.getExpectedDeliveryDate())
                .build();

        List<PurchaseOrderItem> items = dto.getItems().stream().map(itemDto -> {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            return PurchaseOrderItem.builder()
                    .purchaseOrder(po)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .unitPrice(itemDto.getUnitPrice())
                    .build();
        }).collect(Collectors.toList());

        po.setItems(items);
        return mapToDto(purchaseOrderRepository.save(po));
    }

    @Transactional
    public PurchaseOrderDto completePurchaseOrder(Long id) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found"));

        if (po.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalArgumentException("Purchase Order is already completed");
        }

        // Logic to add stock
        // For simplicity, we add stock to a default shelf or we might need enhanced
        // InventoryService logic
        // This part assumes we just want to mark it complete for now, or update
        // inventory if we had logic to pick a shelf.
        // Since InventoryItem requires a Shelf, we can't blindly add to stock without
        // knowing WHERE.
        // For now, let's just update status. In a real app, 'receiving' would ask
        // 'Which shelf?'.

        po.setStatus(OrderStatus.COMPLETED);
        return mapToDto(purchaseOrderRepository.save(po));
    }

    private PurchaseOrderDto mapToDto(PurchaseOrder po) {
        return PurchaseOrderDto.builder()
                .id(po.getId())
                .poCode(po.getPoCode())
                .supplierId(po.getSupplier().getId())
                .supplierName(po.getSupplier().getName())
                .status(po.getStatus())
                .createdAt(po.getCreatedAt())
                .expectedDeliveryDate(po.getExpectedDeliveryDate())
                .items(po.getItems().stream()
                        .map(i -> PurchaseOrderItemDto.builder()
                                .id(i.getId())
                                .productId(i.getProduct().getId())
                                .productName(i.getProduct().getName())
                                .quantity(i.getQuantity())
                                .unitPrice(i.getUnitPrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
