package com.example.ashenbound.controllers;

import com.example.ashenbound.dto.CharacterCreateRequest;
import com.example.ashenbound.exceptions.CharacterCreateNameAlreadyExistsException;
import com.example.ashenbound.services.CharacterService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/v1/character")
@RequiredArgsConstructor
public class CharacterController {

  private final CharacterService characterService;

  @PostMapping("/create")
  public ResponseEntity<?> createCharacter(@RequestBody CharacterCreateRequest request, HttpSession session) {

    String username = (String) session.getAttribute("username");

    if(username == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first");
    }

    try {
      characterService.createCharacter(request, username);
      return ResponseEntity.ok("Character created successfully");
    } catch (CharacterCreateNameAlreadyExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("An error occurred while creating character.");
    }
  }

  @GetMapping("/exists")
  public ResponseEntity<?> checkIfUserHasCharacter(HttpSession session) {

    String username = (String) session.getAttribute("username");

    if(username == null){
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in first.");
    }

    try {
      boolean exists = characterService.checkIfUserHasCharacter(username);
      return ResponseEntity.ok(Map.of("exists", exists)); // Respond with 'exists' true/false
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("An error occurred while checking for character.");
    }
  }
}
