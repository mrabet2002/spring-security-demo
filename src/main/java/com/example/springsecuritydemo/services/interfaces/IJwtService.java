package com.example.springsecuritydemo.services.interfaces;

import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.enums.Role;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface IJwtService {
    String getUserEmail(String token);

    Role getUserRole(String token);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user);

    String generateToken(Map<String, Object> extraClaims, String subject, Key secret, long expiredAfter);

    String generateAccessToken(String refreshToken);

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    User getUserFromToken(String jwt);

    String generateSecretKey() throws NoSuchAlgorithmException;
}
