package com.example.pharmacy.service;

import com.example.pharmacy.dto.MedicineRequest;
import com.example.pharmacy.dto.MedicineResponse;
import com.example.pharmacy.entity.Branch;
import com.example.pharmacy.entity.Category;
import com.example.pharmacy.entity.Medicine;
import com.example.pharmacy.exception.BadRequestException;
import com.example.pharmacy.exception.NotFoundException;
import com.example.pharmacy.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository repository;
    private final CategoryService categoryService;
    private final BranchService branchService;

    public List<MedicineResponse> getAll(Long branchId, String keyword) {
        List<Medicine> list;
        if (branchId != null && keyword != null && !keyword.isBlank()) {
            list = repository.findByBranchIdAndNameContainingIgnoreCase(branchId, keyword);
        } else if (branchId != null) {
            list = repository.findByBranchId(branchId);
        } else if (keyword != null && !keyword.isBlank()) {
            list = repository.findByNameContainingIgnoreCase(keyword);
        } else {
            list = repository.findAll();
        }
        return list.stream().map(this::toResponse).toList();
    }

    public Medicine getEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Khong tim thay thuoc id=" + id));
    }

    public MedicineResponse getById(Long id) {
        return toResponse(getEntityById(id));
    }

    public MedicineResponse create(MedicineRequest req) {
        repository.findByCode(req.code()).ifPresent(m -> {
            throw new BadRequestException("Ma thuoc da ton tai");
        });
        Category category = categoryService.getById(req.categoryId());
        Branch branch = branchService.getById(req.branchId());
        Medicine m = Medicine.builder()
                .code(req.code())
                .name(req.name())
                .unit(req.unit())
                .manufacturer(req.manufacturer())
                .description(req.description())
                .expiryDate(req.expiryDate())
                .quantity(req.quantity())
                .importPrice(req.importPrice())
                .salePrice(req.salePrice())
                .category(category)
                .branch(branch)
                .build();
        return toResponse(repository.save(m));
    }

    public MedicineResponse update(Long id, MedicineRequest req) {
        Medicine m = getEntityById(id);
        m.setCode(req.code());
        m.setName(req.name());
        m.setUnit(req.unit());
        m.setManufacturer(req.manufacturer());
        m.setDescription(req.description());
        m.setExpiryDate(req.expiryDate());
        m.setQuantity(req.quantity());
        m.setImportPrice(req.importPrice());
        m.setSalePrice(req.salePrice());
        m.setCategory(categoryService.getById(req.categoryId()));
        m.setBranch(branchService.getById(req.branchId()));
        return toResponse(repository.save(m));
    }

    public void delete(Long id) {
        repository.delete(getEntityById(id));
    }

    public void reduceStock(Long medicineId, Integer qty) {
        Medicine m = getEntityById(medicineId);
        if (m.getQuantity() < qty) {
            throw new BadRequestException("Thuoc " + m.getName() + " khong du so luong ton kho");
        }
        m.setQuantity(m.getQuantity() - qty);
        repository.save(m);
    }

    public void increaseStock(Long medicineId, Integer qty) {
        Medicine m = getEntityById(medicineId);
        m.setQuantity(m.getQuantity() + qty);
        repository.save(m);
    }

    public List<MedicineResponse> getLowStock(Integer threshold) {
        return repository.findByQuantityLessThanEqual(threshold).stream().map(this::toResponse).toList();
    }

    public List<MedicineResponse> getLowStock(Long branchId, Integer threshold) {
        return getAll(branchId, null).stream().filter(m -> m.quantity() <= threshold).toList();
    }

    public List<MedicineResponse> getExpiring(Integer days) {
        LocalDate endDate = LocalDate.now().plusDays(days);
        return repository.findByExpiryDateLessThanEqual(endDate).stream().map(this::toResponse).toList();
    }

    public List<MedicineResponse> getExpiring(Long branchId, Integer days) {
        LocalDate endDate = LocalDate.now().plusDays(days);
        return getAll(branchId, null).stream()
                .filter(m -> m.expiryDate() != null && !m.expiryDate().isAfter(endDate))
                .toList();
    }

    private MedicineResponse toResponse(Medicine m) {
        return new MedicineResponse(
                m.getId(),
                m.getCode(),
                m.getName(),
                m.getUnit(),
                m.getManufacturer(),
                m.getDescription(),
                m.getExpiryDate(),
                m.getQuantity(),
                m.getImportPrice(),
                m.getSalePrice(),
                m.getCategory() != null ? m.getCategory().getId() : null,
                m.getCategory() != null ? m.getCategory().getName() : null,
                m.getBranch() != null ? m.getBranch().getId() : null,
                m.getBranch() != null ? m.getBranch().getName() : null
        );
    }
}
