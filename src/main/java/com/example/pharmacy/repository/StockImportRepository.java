package com.example.pharmacy.repository;

import com.example.pharmacy.entity.StockImport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockImportRepository extends JpaRepository<StockImport, Long> {
    List<StockImport> findByStaffBranchId(Long branchId);
}
