package com.example.pharmacy.controller;

import com.example.pharmacy.entity.Branch;
import com.example.pharmacy.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/branches") @RequiredArgsConstructor
public class BranchController {
    private final BranchService service;
    @GetMapping public List<Branch> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Branch getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public Branch create(@RequestBody Branch branch) { return service.create(branch); }
    @PutMapping("/{id}") public Branch update(@PathVariable Long id, @RequestBody Branch branch) { return service.update(id, branch); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
