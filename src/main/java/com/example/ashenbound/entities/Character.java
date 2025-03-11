package com.example.ashenbound.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "characters")
@Getter
@Setter
public class Character {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false)
  private int health;

  @Column(nullable = false)
  private int maxHealth;

  @Column(nullable = false)
  private int strength;

  @Column(nullable = false)
  private int defense;

  @Column(nullable = false)
  private int age;

  @OneToOne
  @JoinColumn(name = "user_id", unique = true) //Links character to user
  private User user;

}
