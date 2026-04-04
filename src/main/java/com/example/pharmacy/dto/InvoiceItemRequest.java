package com.example.pharmacy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InvoiceItemRequest(@NotNull Long medicineId, @NotNull @Min(1) Integer quantity) {}
