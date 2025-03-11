package com.example.ashenbound.repository;

import com.example.ashenbound.entities.Character;
import com.example.ashenbound.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
  Optional<Character> findByName(String name);
  Optional<Character> findByUser(User user);
}
