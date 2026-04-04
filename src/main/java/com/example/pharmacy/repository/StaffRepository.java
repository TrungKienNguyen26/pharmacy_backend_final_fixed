package com.example.pharmacy.repository;

import com.example.pharmacy.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByUsername(String username);
    List<Staff> findByBranchId(Long branchId);
    long countByBranchId(Long branchId);
}
