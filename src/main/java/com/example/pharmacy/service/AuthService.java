package com.example.pharmacy.service;

import com.example.pharmacy.dto.AuthResponse;
import com.example.pharmacy.dto.LoginRequest;
import com.example.pharmacy.entity.Branch;
import com.example.pharmacy.entity.Staff;
import com.example.pharmacy.exception.UnauthorizedException;
import com.example.pharmacy.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StaffService staffService;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest req) {
        Staff staff = staffService.findByUsername(req.username());
        if (!Boolean.TRUE.equals(staff.getActive()) || !staffService.checkPassword(staff, req.password())) {
            throw new UnauthorizedException("Sai tai khoan hoac mat khau");
        }

        String token = jwtService.generateToken(staff.getUsername(), staff.getRole().name(), staff.getId());
        Branch branch = staff.getBranch();

        return new AuthResponse(
                token,
                staff.getId(),
                staff.getUsername(),
                staff.getFullName(),
                staff.getRole().name(),
                branch != null ? branch.getId() : null,
                branch != null ? branch.getName() : null
        );
    }
}
