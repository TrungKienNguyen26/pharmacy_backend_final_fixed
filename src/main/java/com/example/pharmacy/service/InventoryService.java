package com.example.pharmacy.service;

import com.example.pharmacy.dto.StockImportRequest;
import com.example.pharmacy.dto.StockImportResponse;
import com.example.pharmacy.dto.StockTransferRequest;
import com.example.pharmacy.dto.StockTransferResponse;
import com.example.pharmacy.entity.Branch;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Staff;
import com.example.pharmacy.entity.StockImport;
import com.example.pharmacy.entity.StockTransfer;
import com.example.pharmacy.exception.BadRequestException;
import com.example.pharmacy.repository.StockImportRepository;
import com.example.pharmacy.repository.StockTransferRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final MedicineService medicineService;
    private final StaffService staffService;
    private final BranchService branchService;
    private final StockImportRepository importRepository;
    private final StockTransferRepository transferRepository;

    @Transactional
    public StockImportResponse importStock(StockImportRequest req) {
        Medicine medicine = medicineService.getEntityById(req.medicineId());
        Staff staff = staffService.getEntityById(req.staffId());
        medicineService.increaseStock(medicine.getId(), req.quantity());
        medicine.setImportPrice(req.importPrice());
        StockImport stockImport = StockImport.builder()
                .createdAt(LocalDateTime.now())
                .medicine(medicine)
                .staff(staff)
                .quantity(req.quantity())
                .importPrice(req.importPrice())
                .note(req.note())
                .build();
        return toImportResponse(importRepository.save(stockImport));
    }

    @Transactional
    public StockTransferResponse transferStock(StockTransferRequest req) {
        if (req.fromBranchId().equals(req.toBranchId())) {
            throw new BadRequestException("Chi nhanh chuyen va nhan khong duoc giong nhau");
        }
        Medicine source = medicineService.getEntityById(req.medicineId());
        if (source.getBranch() == null || !source.getBranch().getId().equals(req.fromBranchId())) {
            throw new BadRequestException("Thuoc khong nam o chi nhanh nguon");
        }
        Staff staff = staffService.getEntityById(req.staffId());
        Branch from = branchService.getById(req.fromBranchId());
        Branch to = branchService.getById(req.toBranchId());
        medicineService.reduceStock(source.getId(), req.quantity());
        StockTransfer transfer = StockTransfer.builder()
                .createdAt(LocalDateTime.now())
                .medicine(source)
                .fromBranch(from)
                .toBranch(to)
                .staff(staff)
                .quantity(req.quantity())
                .note(req.note())
                .build();
        return toTransferResponse(transferRepository.save(transfer));
    }

    public List<StockImportResponse> getImports(Long branchId) {
        List<StockImport> list = branchId == null ? importRepository.findAll() : importRepository.findByStaffBranchId(branchId);
        return list.stream().map(this::toImportResponse).toList();
    }

    public List<StockTransferResponse> getTransfers(Long branchId) {
        List<StockTransfer> list = branchId == null
                ? transferRepository.findAll()
                : transferRepository.findByFromBranchIdOrToBranchId(branchId, branchId);
        return list.stream().map(this::toTransferResponse).toList();
    }

    private StockImportResponse toImportResponse(StockImport item) {
        return new StockImportResponse(
                item.getId(),
                item.getCreatedAt(),
                item.getMedicine().getId(),
                item.getMedicine().getName(),
                item.getStaff().getId(),
                item.getStaff().getFullName(),
                item.getQuantity(),
                item.getImportPrice(),
                item.getNote()
        );
    }

    private StockTransferResponse toTransferResponse(StockTransfer item) {
        return new StockTransferResponse(
                item.getId(),
                item.getCreatedAt(),
                item.getMedicine().getId(),
                item.getMedicine().getName(),
                item.getFromBranch().getId(),
                item.getFromBranch().getName(),
                item.getToBranch().getId(),
                item.getToBranch().getName(),
                item.getStaff().getId(),
                item.getStaff().getFullName(),
                item.getQuantity(),
                item.getNote()
        );
    }
}
