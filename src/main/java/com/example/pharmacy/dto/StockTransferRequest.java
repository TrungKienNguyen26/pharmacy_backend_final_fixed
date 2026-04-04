package com.example.pharmacy.dto;

import jakarta.validation.constraints.*;

public record StockTransferRequest(@NotNull Long medicineId, @NotNull Long fromBranchId,
                                   @NotNull Long toBranchId, @NotNull Long staffId,
                                   @NotNull @Min(1) Integer quantity, String note) {}
