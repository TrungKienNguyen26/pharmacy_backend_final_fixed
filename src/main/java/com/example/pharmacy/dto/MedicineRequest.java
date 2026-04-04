package com.example.pharmacy.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MedicineRequest(
        @NotBlank String code,
        @NotBlank String name,
        String unit,
        String manufacturer,
        String description,
        LocalDate expiryDate,
        @NotNull @Min(0) Integer quantity,
        @NotNull @DecimalMin("0.0") BigDecimal importPrice,
        @NotNull @DecimalMin("0.0") BigDecimal salePrice,
        @NotNull Long categoryId,
        @NotNull Long branchId
) {}
