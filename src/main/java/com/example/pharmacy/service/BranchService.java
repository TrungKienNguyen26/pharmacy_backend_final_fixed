package com.example.pharmacy.service;

import com.example.pharmacy.entity.Branch;
import com.example.pharmacy.exception.NotFoundException;
import com.example.pharmacy.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class BranchService {
    private final BranchRepository repository;
    public List<Branch> getAll() { return repository.findAll(); }
    public Branch getById(Long id) { return repository.findById(id).orElseThrow(() -> new NotFoundException("Khong tim thay chi nhanh id=" + id)); }
    public Branch create(Branch branch) { return repository.save(branch); }
    public Branch update(Long id, Branch req) {
        Branch b = getById(id);
        b.setCode(req.getCode()); b.setName(req.getName()); b.setAddress(req.getAddress()); b.setPhone(req.getPhone());
        return repository.save(b);
    }
    public void delete(Long id) { repository.delete(getById(id)); }
}
