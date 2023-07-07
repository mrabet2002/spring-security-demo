package com.example.springsecuritydemo.services.interfaces;

import com.example.springsecuritydemo.entities.User;

import java.util.Date;
import java.util.Map;

public interface IJwtService {
    String getUserEmailFromToken(String token);

    Date getTokenExpiration(String token);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user);

    boolean isTokenExpired(String token);

    boolean isTokenValid(String token, User user);
}
