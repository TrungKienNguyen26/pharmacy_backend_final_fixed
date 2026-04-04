package com.example.pharmacy.dto;

public record AuthResponse(
        String token,
        Long userId,
        String username,
        String fullName,
        String role,
        Long branchId,
        String branchName
) {}
