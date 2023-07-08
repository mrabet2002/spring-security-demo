package com.example.springsecuritydemo.services.interfaces;

import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.enums.Role;

import java.util.Date;
import java.util.Map;

public interface IJwtService {
    String getUserEmail(String token);

    Role getUserRole(String token);

    Date getTokenExpiration(String token);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user);

    String generateAccessToken(Map<String, Object> extraClaims, User user);

    String generateRefreshToken(Map<String, Object> extraClaims, User user);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, User user);

    User getUserFromToken(String jwt);
}
