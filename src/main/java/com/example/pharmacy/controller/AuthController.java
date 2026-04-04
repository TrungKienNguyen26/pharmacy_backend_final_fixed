package com.example.pharmacy.controller;

import com.example.pharmacy.dto.AuthResponse;
import com.example.pharmacy.dto.LoginRequest;
import com.example.pharmacy.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) { return authService.login(request); }
}
