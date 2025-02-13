package com.example.ashenbound.services;

import com.example.ashenbound.dto.AuthResponse;
import com.example.ashenbound.dto.LoginRequest;
import com.example.ashenbound.dto.RegisterRequest;
import com.example.ashenbound.entities.User;
import com.example.ashenbound.exceptions.UserAlreadyExistsException;
import com.example.ashenbound.repository.UserRepository;
import com.example.ashenbound.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Marks this class as a service for Spring to manage.
@RequiredArgsConstructor // Generates a constructor for all final fields for dependency injection.
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtils jwtUtils;

  public AuthResponse register(RegisterRequest request) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("User already exists!");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    // Encrypt the password before saving.
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    userRepository.save(user);

    // Generate a JWT token for the new user.
    String token = jwtUtils.generateToken(user);
    return new AuthResponse(token);
  }

  public AuthResponse login(LoginRequest request) {
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found!"));

    // Check if the provided password matches the stored encrypted password.
    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid credentials!");
    }

    String token = jwtUtils.generateToken(user);
    return new AuthResponse(token);
  }
}
