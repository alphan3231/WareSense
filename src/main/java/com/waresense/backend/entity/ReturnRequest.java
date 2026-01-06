package com.waresense.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "return_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime requestDate;

    // Status: PENDING, APPROVED, REJECTED
    @Column(nullable = false)
    private String status;
}
