package com.example.pharmacy.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record StockImportRequest(@NotNull Long medicineId, @NotNull Long staffId,
                                 @NotNull @Min(1) Integer quantity,
                                 @NotNull @DecimalMin("0.0") BigDecimal importPrice,
                                 String note) {}
