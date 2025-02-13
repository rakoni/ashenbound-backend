package com.example.ashenbound.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @Column(unique = true, nullable = false)
  private String email;

  public User(){

  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setUsername(String username){
    this.username = username;
  }

  public String getUsername(){
    return username;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail(){
    return  email;
  }
}
