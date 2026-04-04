package com.example.pharmacy.dto;

import com.example.pharmacy.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StaffRequest(@NotBlank String fullName, @NotBlank String username,
                           @NotBlank String password, @NotNull Role role,
                           String phone, Boolean active, Long branchId) {}
