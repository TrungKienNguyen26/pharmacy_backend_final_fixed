package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    Optional<Medicine> findByCode(String code);
    List<Medicine> findByBranchId(Long branchId);
    List<Medicine> findByBranchIdAndNameContainingIgnoreCase(Long branchId, String keyword);
    List<Medicine> findByNameContainingIgnoreCase(String keyword);
    List<Medicine> findByQuantityLessThanEqual(Integer quantity);
    List<Medicine> findByExpiryDateLessThanEqual(LocalDate date);
}
