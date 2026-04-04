package com.example.pharmacy.dto;

import java.math.BigDecimal;

public record DashboardResponse(
        Long branchId,
        String branchName,
        long medicineCount,
        long invoiceCount,
        BigDecimal revenueToday,
        long lowStockCount,
        long expiringCount,
        long staffCount
) {}
