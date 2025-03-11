package com.example.ashenbound.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  private String email;
  private String username;
  private String password;
}
