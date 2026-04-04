package com.example.pharmacy.controller;

import com.example.pharmacy.entity.Category;
import com.example.pharmacy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/api/categories") @RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;
    @GetMapping public List<Category> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public Category getById(@PathVariable Long id) { return service.getById(id); }
    @PostMapping public Category create(@RequestBody Category category) { return service.create(category); }
    @PutMapping("/{id}") public Category update(@PathVariable Long id, @RequestBody Category category) { return service.update(id, category); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}
