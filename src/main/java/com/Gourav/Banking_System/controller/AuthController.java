package com.Gourav.Banking_System.controller;

import com.Gourav.Banking_System.dto.AuthResponse;
import com.Gourav.Banking_System.dto.LoginRequest;
import com.Gourav.Banking_System.dto.RegisterRequest;
import com.Gourav.Banking_System.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(
            AuthService authService) {

        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        return new ResponseEntity<>(
                authService.register(request),
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request));
    }
}