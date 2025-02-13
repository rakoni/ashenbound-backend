package com.example.ashenbound.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;

  public JwtAuthenticationFilter(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    try {
      // Extract JWT token from the request header.
      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        // Get user email from token.
        String userEmail = jwtUtils.getEmailFromJwtToken(jwt);

        // Create an authentication token (without authorities for simplicity).
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userEmail, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set the authentication in the SecurityContext.
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      System.err.println("Cannot set user authentication: " + e.getMessage());
    }

    // Continue processing the request.
    filterChain.doFilter(request, response);
  }

  // Helper method to extract JWT from Authorization header.
  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7); // Remove "Bearer " prefix.
    }
    return null;
  }
}
