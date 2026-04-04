package com.example.pharmacy.controller;

import com.example.pharmacy.dto.StaffRequest;
import com.example.pharmacy.dto.StaffResponse;
import com.example.pharmacy.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staffs")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService service;

    @GetMapping
    public List<StaffResponse> getAll(@RequestParam(required = false) Long branchId) {
        return branchId == null ? service.getAll() : service.getByBranch(branchId);
    }

    // Backward-compatible route for older Flutter builds
    @GetMapping("/branch/{branchId}")
    public List<StaffResponse> getByBranch(@PathVariable Long branchId) {
        return service.getByBranch(branchId);
    }

    @GetMapping("/{id}")
    public StaffResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public StaffResponse create(@Valid @RequestBody StaffRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public StaffResponse update(@PathVariable Long id, @Valid @RequestBody StaffRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
