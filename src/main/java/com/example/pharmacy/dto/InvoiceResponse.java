package com.example.pharmacy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record InvoiceResponse(Long id, String invoiceCode, String customerName, String customerPhone,
                              BigDecimal totalAmount, LocalDateTime createdAt,
                              Long branchId, String branchName, Long staffId, String staffName,
                              List<ItemResponse> items) {
    public record ItemResponse(Long medicineId, String medicineName, Integer quantity, BigDecimal unitPrice, BigDecimal lineTotal) {}
}
