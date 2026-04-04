package com.example.pharmacy.controller;

import com.example.pharmacy.dto.MedicineRequest;
import com.example.pharmacy.dto.MedicineResponse;
import com.example.pharmacy.service.MedicineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService service;

    @GetMapping
    public List<MedicineResponse> getAll(@RequestParam(required = false) Long branchId,
                                         @RequestParam(required = false) String keyword) {
        return service.getAll(branchId, keyword);
    }

    // Backward-compatible route for older Flutter builds
    @GetMapping("/branch/{branchId}")
    public List<MedicineResponse> getByBranch(@PathVariable Long branchId) {
        return service.getAll(branchId, null);
    }

    @GetMapping("/{id}")
    public MedicineResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/alerts/low-stock")
    public List<MedicineResponse> lowStock(@RequestParam(defaultValue = "10") Integer threshold) {
        return service.getLowStock(threshold);
    }

    @GetMapping("/alerts/expiring")
    public List<MedicineResponse> expiring(@RequestParam(defaultValue = "30") Integer days) {
        return service.getExpiring(days);
    }

    @PostMapping
    public MedicineResponse create(@Valid @RequestBody MedicineRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public MedicineResponse update(@PathVariable Long id, @Valid @RequestBody MedicineRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
