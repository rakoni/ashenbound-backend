package com.example.ashenbound.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Character {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int health;
  private int maxHealth;
  private int strength;
  private int defense;
  private int age;

  public Character(){

  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId(){
    return id;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public void setHealth(int health){
    this.health = health;
  }

  public int getHealth(){
    return health;
  }

  public void setMaxHealth(int maxHealth) {
    this.maxHealth = maxHealth;
  }

  public int getMaxHealth() {
    return maxHealth;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public int getStrength(){
    return strength;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public int getDefense(){
    return defense;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getAge(){
    return age;
  }
}
