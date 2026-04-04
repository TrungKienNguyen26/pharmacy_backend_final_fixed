package com.example.pharmacy.dto;

public record StaffResponse(
        Long id,
        String fullName,
        String username,
        String role,
        String phone,
        Boolean active,
        Long branchId,
        String branchName
) {}
