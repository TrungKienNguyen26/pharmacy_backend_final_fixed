package com.example.pharmacy.repository;

import com.example.pharmacy.entity.StockTransfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockTransferRepository extends JpaRepository<StockTransfer, Long> {
    List<StockTransfer> findByFromBranchIdOrToBranchId(Long fromBranchId, Long toBranchId);
}
