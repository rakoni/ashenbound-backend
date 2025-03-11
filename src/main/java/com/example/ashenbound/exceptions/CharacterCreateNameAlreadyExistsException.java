package com.example.ashenbound.exceptions;

public class CharacterCreateNameAlreadyExistsException extends RuntimeException{
  public CharacterCreateNameAlreadyExistsException(String message){
    super(message);
  }
}
