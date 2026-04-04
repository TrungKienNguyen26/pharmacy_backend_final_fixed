package com.example.pharmacy.service;

import com.example.pharmacy.dto.DashboardResponse;
import com.example.pharmacy.entity.Branch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final MedicineService medicineService;
    private final InvoiceService invoiceService;
    private final BranchService branchService;
    private final StaffService staffService;

    public DashboardResponse getSummary(Long branchId) {
        Branch branch = null;
        if (branchId != null) {
            branch = branchService.getById(branchId);
        }
        long medicineCount = medicineService.getAll(branchId, null).size();
        long invoiceCount = invoiceService.getAll(branchId).size();
        var revenueToday = invoiceService.revenueToday(branchId);
        long lowStockCount = medicineService.getLowStock(branchId, 10).size();
        long expiringCount = medicineService.getExpiring(branchId, 30).size();
        long staffCount = branchId == null ? staffService.getAll().size() : staffService.countByBranch(branchId);
        return new DashboardResponse(
                branch != null ? branch.getId() : null,
                branch != null ? branch.getName() : "Toan he thong",
                medicineCount,
                invoiceCount,
                revenueToday,
                lowStockCount,
                expiringCount,
                staffCount
        );
    }
}
