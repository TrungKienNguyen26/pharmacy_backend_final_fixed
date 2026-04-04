package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByBranchId(Long branchId);
    List<Invoice> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<Invoice> findByBranchIdAndCreatedAtBetween(Long branchId, LocalDateTime start, LocalDateTime end);
}
