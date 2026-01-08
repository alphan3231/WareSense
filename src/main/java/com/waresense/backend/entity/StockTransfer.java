package com.waresense.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String transferCode;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "source_shelf_id", nullable = false)
    private Shelf sourceShelf;

    @ManyToOne
    @JoinColumn(name = "target_shelf_id", nullable = false)
    private Shelf targetShelf;

    @Column(nullable = false)
    private Integer quantity;

    @CreationTimestamp
    private LocalDateTime transferDate;

    @ManyToOne
    @JoinColumn(name = "performed_by_id", nullable = false)
    private User performedBy;
}
