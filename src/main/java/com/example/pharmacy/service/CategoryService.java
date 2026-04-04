package com.example.pharmacy.service;

import com.example.pharmacy.entity.Category;
import com.example.pharmacy.exception.NotFoundException;
import com.example.pharmacy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    public List<Category> getAll() { return repository.findAll(); }
    public Category getById(Long id) { return repository.findById(id).orElseThrow(() -> new NotFoundException("Khong tim thay danh muc id=" + id)); }
    public Category create(Category category) { return repository.save(category); }
    public Category update(Long id, Category req) { Category c = getById(id); c.setName(req.getName()); c.setDescription(req.getDescription()); return repository.save(c); }
    public void delete(Long id) { repository.delete(getById(id)); }
}
