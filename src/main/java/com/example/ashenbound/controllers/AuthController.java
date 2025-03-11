package com.example.ashenbound.controllers;

import com.example.ashenbound.dto.LoginRequest;
import com.example.ashenbound.dto.RegisterRequest;
import com.example.ashenbound.entities.User;
import com.example.ashenbound.services.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpServletResponse response) {
    try {
      // Register the user and retrieve the registered user object or any needed response
      User registeredUser = authService.register(request); // The AuthService will handle registration

      // Return a successful response (you can send back a custom message or object)
      return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful for user: " + registeredUser.getUsername());

    } catch (Exception e) {
      return ResponseEntity.status(500).body("Registration failed: " + e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {

    try{

      String authentication = authService.login(request);
      session.setAttribute("user", request.getUsername());
      return ResponseEntity.ok(authentication);

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<?> logout(HttpServletResponse response) {
    return ResponseEntity.ok("Logged out successfully");
  }
}
