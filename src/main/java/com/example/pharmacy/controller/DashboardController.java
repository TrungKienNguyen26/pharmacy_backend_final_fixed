package com.example.pharmacy.controller;

import com.example.pharmacy.dto.DashboardResponse;
import com.example.pharmacy.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService service;

    @GetMapping("/summary")
    public DashboardResponse summary(@RequestParam(required = false) Long branchId) {
        return service.getSummary(branchId);
    }

    // Backward-compatible routes for older Flutter builds
    @GetMapping("/summary/branch/{branchId}")
    public DashboardResponse summaryByBranch(@PathVariable Long branchId) {
        return service.getSummary(branchId);
    }

    @GetMapping("/summary/all")
    public DashboardResponse summaryAll() {
        return service.getSummary(null);
    }
}
