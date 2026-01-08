package com.waresense.backend.service;

import com.waresense.backend.dto.ShipmentDto;
import com.waresense.backend.entity.Order;
import com.waresense.backend.entity.Shipment;
import com.waresense.backend.exception.ResourceNotFoundException;
import com.waresense.backend.repository.OrderRepository;
import com.waresense.backend.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    public List<ShipmentDto> getAllShipments() {
        return shipmentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ShipmentDto getShipmentById(Long id) {
        return shipmentRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));
    }

    @Transactional
    public ShipmentDto createShipment(ShipmentDto dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (shipmentRepository.findByOrderId(order.getId()).isPresent()) {
            throw new IllegalArgumentException("Shipment already exists for this order");
        }

        Shipment shipment = Shipment.builder()
                .trackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase())
                .order(order)
                .carrier(dto.getCarrier())
                .status("PREPARED")
                .estimatedArrival(dto.getEstimatedArrival())
                .build();

        return mapToDto(shipmentRepository.save(shipment));
    }

    @Transactional
    public ShipmentDto updateShipmentStatus(Long id, String status) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        shipment.setStatus(status);
        return mapToDto(shipmentRepository.save(shipment));
    }

    private ShipmentDto mapToDto(Shipment shipment) {
        return ShipmentDto.builder()
                .id(shipment.getId())
                .trackingNumber(shipment.getTrackingNumber())
                .orderId(shipment.getOrder().getId())
                .orderCode(shipment.getOrder().getOrderCode())
                .carrier(shipment.getCarrier())
                .status(shipment.getStatus())
                .shipmentDate(shipment.getShipmentDate())
                .estimatedArrival(shipment.getEstimatedArrival())
                .build();
    }
}
