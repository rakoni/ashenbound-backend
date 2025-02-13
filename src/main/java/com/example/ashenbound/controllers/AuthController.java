package com.example.ashenbound.controllers;

import com.example.ashenbound.dto.AuthResponse;
import com.example.ashenbound.dto.LoginRequest;
import com.example.ashenbound.dto.RegisterRequest;
import com.example.ashenbound.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    // Delegates to the AuthService to handle registration.
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    // Delegates to the AuthService to handle login.
    return ResponseEntity.ok(authService.login(request));
  }
}
