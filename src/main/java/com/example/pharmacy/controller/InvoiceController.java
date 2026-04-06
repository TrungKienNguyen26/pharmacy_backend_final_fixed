package com.example.pharmacy.controller;

import com.example.pharmacy.dto.CreateInvoiceRequest;
import com.example.pharmacy.dto.InvoiceResponse;
import com.example.pharmacy.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService service;

    @GetMapping
    public List<InvoiceResponse> getAll(@RequestParam(required = false) Long branchId) {
        return service.getAll(branchId);
    }
    @GetMapping("/customer-by-phone")
    public String findCustomerByPhone(@RequestParam String phone) {
        return service.findCustomerNameByPhone(phone);
    }
    // Backward-compatible route for older Flutter builds
    @GetMapping("/branch/{branchId}")
    public List<InvoiceResponse> getByBranch(@PathVariable Long branchId) {
        return service.getAll(branchId);
    }

    @GetMapping("/{id}")
    public InvoiceResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public InvoiceResponse create(@Valid @RequestBody CreateInvoiceRequest request) {
        return service.create(request);
    }
}
