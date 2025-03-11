package com.example.ashenbound.services;

import com.example.ashenbound.dto.CharacterCreateRequest;
import com.example.ashenbound.entities.Character;
import com.example.ashenbound.entities.User;
import com.example.ashenbound.repository.CharacterRepository;
import com.example.ashenbound.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterService {

  private final CharacterRepository characterRepository;
  private final UserRepository userRepository;


  public void createCharacter(CharacterCreateRequest request, String username){

    //Fetch user from DB
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    //Check if user already has a character
    if(characterRepository.findByUser(user).isPresent()) {
      throw new RuntimeException("User already has a character");
    }

    //Check if the name is already taken
    if(characterRepository.findByName(request.getName()).isPresent()) {
      throw new RuntimeException("There is already a character with that name");
    }

    //Create new character and assign it to the user
    Character character = new Character();
    character.setName(request.getName());
    character.setAge(0);
    character.setMaxHealth(100);
    character.setHealth(100);
    character.setStrength(10);
    character.setDefense(10);
    character.setUser(user); // Associate character with user

    characterRepository.save(character);
  }

  public boolean checkIfUserHasCharacter(String username) {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

    return characterRepository.findByUser(user).isPresent(); // Check if user already has a character
  }
}
