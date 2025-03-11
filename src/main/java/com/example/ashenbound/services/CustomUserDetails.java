package com.example.ashenbound.services;

import com.example.ashenbound.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

  private final User user;

  // Constructor to initialize the User object
  public CustomUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // Return user roles as authorities (you can modify this if you have roles in your User entity)
    return new ArrayList<>();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail(); // You can use getUsername() if you prefer.
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; // Assuming account is not expired.
  }

  @Override
  public boolean isAccountNonLocked() {
    return true; // Assuming account is not locked.
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; // Assuming credentials are not expired.
  }

  @Override
  public boolean isEnabled() {
    return true; // Assuming account is enabled.
  }

  // You can add more methods based on your needs
  public User getUser() {
    return user;
  }
}