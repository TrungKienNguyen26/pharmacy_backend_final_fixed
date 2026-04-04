package com.example.pharmacy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateInvoiceRequest(String customerName, String customerPhone,
                                   @NotNull Long branchId, @NotNull Long staffId,
                                   @NotEmpty List<InvoiceItemRequest> items) {}
