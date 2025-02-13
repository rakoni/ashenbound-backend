package com.example.ashenbound.security;

import com.example.ashenbound.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

@Component // Makes this class a Spring Bean.
public class JwtUtils {

  // Secret key to sign the JWT. In a real application, this should be stored securely.
  @Value("${jwt.secret}")
  private String jwtSecret;

  // Token validity duration (e.g., 86400000ms = 24 hours).
  @Value("${jwt.expirationMs}")
  private int jwtExpirationMs;

  // Helper method to get the signing key (as a Key object) from the secret.
  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  // Generate a JWT token based on user details.
  public String generateToken(User user) {
    return Jwts.builder()
            .setSubject(user.getEmail()) // Set subject as the user's email.
            .setIssuedAt(new Date()) // Set issue time.
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Set expiration time.
            .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Sign the token with HS512 algorithm and secret key.
            .compact();
  }

  // Extract email (subject) from JWT token.
  public String getEmailFromJwtToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  // Validate the JWT token.
  public boolean validateJwtToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
      System.err.println("Invalid JWT token: " + e.getMessage());
    } catch (ExpiredJwtException e) {
      System.err.println("JWT token is expired: " + e.getMessage());
    } catch (UnsupportedJwtException e) {
      System.err.println("JWT token is unsupported: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.err.println("JWT claims string is empty: " + e.getMessage());
    }
    return false;
  }
}
