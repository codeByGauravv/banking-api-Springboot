package com.Gourav.Banking_System.service;

import com.Gourav.Banking_System.dto.AuthResponse;
import com.Gourav.Banking_System.dto.LoginRequest;
import com.Gourav.Banking_System.dto.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}