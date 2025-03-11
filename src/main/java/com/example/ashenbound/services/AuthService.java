package com.example.ashenbound.services;


import com.example.ashenbound.dto.LoginRequest;
import com.example.ashenbound.dto.RegisterRequest;
import com.example.ashenbound.entities.User;
import com.example.ashenbound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Marks this class as a service for Spring to manage.
@RequiredArgsConstructor // Generates a constructor for all final fields for dependency injection.
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User register(RegisterRequest request) throws Exception {

    // Check if the user already exists
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new RuntimeException("Username is already taken.");
    }

    // Create the user object
    User newUser = new User();
    newUser.setUsername(request.getUsername());
    newUser.setPassword(new BCryptPasswordEncoder().encode(request.getPassword())); // Encrypt password
    newUser.setEmail(request.getEmail());

    // Save the user to the database
    return userRepository.save(newUser);
  }

  public String login(LoginRequest request) {

    // Retrieve the actual User entity from the repository using the username
    User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if(passwordEncoder.matches(request.getPassword(), user.getPassword())){
      return "Login successful";
    }
    throw new RuntimeException("Invalid credentials");
  }

}
