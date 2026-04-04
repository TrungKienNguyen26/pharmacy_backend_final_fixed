package com.example.pharmacy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StockImportResponse(
        Long id,
        LocalDateTime createdAt,
        Long medicineId,
        String medicineName,
        Long staffId,
        String staffName,
        Integer quantity,
        BigDecimal importPrice,
        String note
) {}
