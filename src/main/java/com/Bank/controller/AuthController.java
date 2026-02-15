package com.Bank.controller;

import com.Bank.dto.LoginRequestDto;
import com.Bank.dto.AuthResponseDto;
import com.Bank.security.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.Bank.security.CustomUserDetails;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public AuthResponseDto login(
            @Valid @RequestBody LoginRequestDto request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        // ✅ Correct way
        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        Long userId = user.getId();
        String role = user.getRole().name();

        String token = jwtUtil.generateToken(userId, role);

        return new AuthResponseDto(token);
    }
}
