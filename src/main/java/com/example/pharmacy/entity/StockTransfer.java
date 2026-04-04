package com.example.pharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transfers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StockTransfer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_branch_id")
    private Branch fromBranch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_branch_id")
    private Branch toBranch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @Column(nullable = false)
    private Integer quantity;

    private String note;
}
