package com.waresense.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String trackingNumber;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String carrier; // DHL, FedEx, etc.

    private String status; // PREPARED, SHIPPED, DELIVERED

    @CreationTimestamp
    private LocalDateTime shipmentDate;

    private LocalDateTime estimatedArrival;
}
