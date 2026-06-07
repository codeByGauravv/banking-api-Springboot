package com.Gourav.Banking_System.Implimentations;

import com.Gourav.Banking_System.dto.AuthResponse;
import com.Gourav.Banking_System.dto.LoginRequest;
import com.Gourav.Banking_System.dto.RegisterRequest;
import com.Gourav.Banking_System.entity.User;
import com.Gourav.Banking_System.enums.Role;
import com.Gourav.Banking_System.repositories.UserRepository;
import com.Gourav.Banking_System.security.JwtService;
import com.Gourav.Banking_System.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceIMPL implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceIMPL(
            JwtService jwtService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {

        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String register(RegisterRequest request) {

        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }


    @Override
    public AuthResponse login(LoginRequest request) {

        System.out.println("USERNAME=[" + request.username() + "]");
        System.out.println("PASSWORD=[" + request.password() + "]");

        System.out.println(
                userRepository.findByUsername(request.username())
        );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        User user = userRepository
                .findByUsername(request.username())
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        String token = jwtService.generateToken(
                user.getUsername());

        return new AuthResponse(token);
    }
}