package com.example.pharmacy.controller;

import com.example.pharmacy.dto.StockImportRequest;
import com.example.pharmacy.dto.StockImportResponse;
import com.example.pharmacy.dto.StockTransferRequest;
import com.example.pharmacy.dto.StockTransferResponse;
import com.example.pharmacy.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService service;

    @PostMapping("/import")
    public StockImportResponse importStock(@Valid @RequestBody StockImportRequest request) {
        return service.importStock(request);
    }

    @PostMapping("/transfer")
    public StockTransferResponse transfer(@Valid @RequestBody StockTransferRequest request) {
        return service.transferStock(request);
    }

    @GetMapping("/imports")
    public List<StockImportResponse> getImports(@RequestParam(required = false) Long branchId) {
        return service.getImports(branchId);
    }

    @GetMapping("/transfers")
    public List<StockTransferResponse> getTransfers(@RequestParam(required = false) Long branchId) {
        return service.getTransfers(branchId);
    }
}
