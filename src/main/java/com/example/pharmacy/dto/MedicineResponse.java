package com.example.pharmacy.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MedicineResponse(Long id, String code, String name, String unit, String manufacturer,
                               String description, LocalDate expiryDate, Integer quantity,
                               BigDecimal importPrice, BigDecimal salePrice,
                               Long categoryId, String categoryName,
                               Long branchId, String branchName) {}
