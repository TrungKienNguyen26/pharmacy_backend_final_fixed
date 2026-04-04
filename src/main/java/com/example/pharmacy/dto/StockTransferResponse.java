package com.example.pharmacy.dto;

import java.time.LocalDateTime;

public record StockTransferResponse(
        Long id,
        LocalDateTime createdAt,
        Long medicineId,
        String medicineName,
        Long fromBranchId,
        String fromBranchName,
        Long toBranchId,
        String toBranchName,
        Long staffId,
        String staffName,
        Integer quantity,
        String note
) {}
