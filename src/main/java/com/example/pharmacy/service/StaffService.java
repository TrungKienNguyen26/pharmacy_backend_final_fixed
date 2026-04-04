package com.example.pharmacy.service;

import com.example.pharmacy.dto.StaffRequest;
import com.example.pharmacy.dto.StaffResponse;
import com.example.pharmacy.entity.Branch;
import com.example.pharmacy.entity.Staff;
import com.example.pharmacy.exception.BadRequestException;
import com.example.pharmacy.exception.NotFoundException;
import com.example.pharmacy.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository repository;
    private final BranchService branchService;
    private final PasswordEncoder passwordEncoder;

    public List<StaffResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public List<StaffResponse> getByBranch(Long branchId) {
        return repository.findByBranchId(branchId).stream().map(this::toResponse).toList();
    }

    public long countByBranch(Long branchId) {
        return repository.countByBranchId(branchId);
    }

    public Staff getEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Khong tim thay nhan vien id=" + id));
    }

    public StaffResponse getById(Long id) {
        return toResponse(getEntityById(id));
    }

    public Staff findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() -> new NotFoundException("Khong tim thay user"));
    }

    public StaffResponse create(StaffRequest req) {
        repository.findByUsername(req.username()).ifPresent(s -> {
            throw new BadRequestException("Username da ton tai");
        });
        Staff staff = Staff.builder()
                .fullName(req.fullName())
                .username(req.username())
                .password(passwordEncoder.encode(req.password()))
                .role(req.role())
                .phone(req.phone())
                .active(req.active() == null ? true : req.active())
                .branch(req.branchId() == null ? null : branchService.getById(req.branchId()))
                .build();
        return toResponse(repository.save(staff));
    }

    public StaffResponse update(Long id, StaffRequest req) {
        Staff staff = getEntityById(id);
        if (!staff.getUsername().equals(req.username())) {
            repository.findByUsername(req.username()).ifPresent(s -> {
                throw new BadRequestException("Username da ton tai");
            });
        }
        staff.setFullName(req.fullName());
        staff.setUsername(req.username());
        if (req.password() != null && !req.password().isBlank()) {
            staff.setPassword(passwordEncoder.encode(req.password()));
        }
        staff.setRole(req.role());
        staff.setPhone(req.phone());
        staff.setActive(req.active() == null ? true : req.active());
        staff.setBranch(req.branchId() == null ? null : branchService.getById(req.branchId()));
        return toResponse(repository.save(staff));
    }

    public boolean checkPassword(Staff staff, String rawPassword) {
        return passwordEncoder.matches(rawPassword, staff.getPassword());
    }

    public void delete(Long id) {
        repository.delete(getEntityById(id));
    }

    public StaffResponse toResponse(Staff staff) {
        Branch branch = staff.getBranch();
        return new StaffResponse(
                staff.getId(),
                staff.getFullName(),
                staff.getUsername(),
                staff.getRole().name(),
                staff.getPhone(),
                staff.getActive(),
                branch != null ? branch.getId() : null,
                branch != null ? branch.getName() : null
        );
    }
}
