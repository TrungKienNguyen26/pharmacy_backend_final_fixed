package com.example.pharmacy.service;

import com.example.pharmacy.dto.CreateInvoiceRequest;
import com.example.pharmacy.dto.InvoiceResponse;
import com.example.pharmacy.entity.Branch;
import com.example.pharmacy.entity.Invoice;
import com.example.pharmacy.entity.InvoiceItem;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.entity.Staff;
import com.example.pharmacy.exception.BadRequestException;
import com.example.pharmacy.exception.NotFoundException;
import com.example.pharmacy.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository repository;
    private final BranchService branchService;
    private final StaffService staffService;
    private final MedicineService medicineService;

    public List<InvoiceResponse> getAll(Long branchId) {
        List<Invoice> list = branchId == null ? repository.findAll() : repository.findByBranchId(branchId);
        return list.stream().map(this::toResponse).toList();
    }

    public InvoiceResponse getById(Long id) {
        return toResponse(getEntityById(id));
    }

    public Invoice getEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Khong tim thay hoa don id=" + id));
    }
    public String findCustomerNameByPhone(String phone) {
        Invoice invoice = repository
                .findTopByCustomerPhoneOrderByCreatedAtDesc(phone)
                .orElseThrow(() -> new NotFoundException("Khong tim thay khach hang"));

        return invoice.getCustomerName();
    }
    @Transactional
    public InvoiceResponse create(CreateInvoiceRequest req) {
        Branch branch = branchService.getById(req.branchId());
        Staff staff = staffService.getEntityById(req.staffId());
        if (staff.getBranch() == null || !staff.getBranch().getId().equals(branch.getId())) {
            throw new BadRequestException("Nhan vien khong thuoc chi nhanh nay");
        }

        Invoice invoice = Invoice.builder()
                .invoiceCode("HD-" + String.format("%06d", repository.count() + 1))
                .customerName(req.customerName())
                .customerPhone(req.customerPhone())
                .createdAt(LocalDateTime.now())
                .branch(branch)
                .staff(staff)
                .build();

        BigDecimal total = BigDecimal.ZERO;
        for (var itemReq : req.items()) {
            Medicine medicine = medicineService.getEntityById(itemReq.medicineId());
            if (medicine.getBranch() == null || !medicine.getBranch().getId().equals(branch.getId())) {
                throw new BadRequestException("Thuoc khong thuoc chi nhanh nay");
            }
            if (medicine.getQuantity() < itemReq.quantity()) {
                throw new BadRequestException("Thuoc " + medicine.getName() + " khong du so luong");
            }
            BigDecimal unitPrice = medicine.getSalePrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(itemReq.quantity()));
            InvoiceItem item = InvoiceItem.builder()
                    .invoice(invoice)
                    .medicine(medicine)
                    .quantity(itemReq.quantity())
                    .unitPrice(unitPrice)
                    .lineTotal(lineTotal)
                    .build();
            invoice.getItems().add(item);
            total = total.add(lineTotal);
            medicineService.reduceStock(medicine.getId(), itemReq.quantity());
        }
        invoice.setTotalAmount(total);
        return toResponse(repository.save(invoice));
    }

    public BigDecimal revenueToday() {
        return revenueToday(null);
    }

    public BigDecimal revenueToday(Long branchId) {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);
        List<Invoice> list = branchId == null
                ? repository.findByCreatedAtBetween(start, end)
                : repository.findByBranchIdAndCreatedAtBetween(branchId, start, end);
        return list.stream().map(Invoice::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long countAll() {
        return repository.count();
    }

    private InvoiceResponse toResponse(Invoice invoice) {
        List<InvoiceResponse.ItemResponse> items = invoice.getItems().stream().map(item -> new InvoiceResponse.ItemResponse(
                item.getMedicine().getId(),
                item.getMedicine().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getLineTotal())).toList();
        return new InvoiceResponse(
                invoice.getId(),
                invoice.getInvoiceCode(),
                invoice.getCustomerName(),
                invoice.getCustomerPhone(),
                invoice.getTotalAmount(),
                invoice.getCreatedAt(),
                invoice.getBranch().getId(),
                invoice.getBranch().getName(),
                invoice.getStaff().getId(),
                invoice.getStaff().getFullName(),
                items
        );
    }
}
