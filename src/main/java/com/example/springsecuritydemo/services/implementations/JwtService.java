package com.example.springsecuritydemo.services.implementations;

import com.example.springsecuritydemo.config.JwtConfig;
import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.services.interfaces.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService implements IJwtService {
    private final JwtConfig jwtConfig;

    @Override
    public String getUserEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Date getTokenExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    @Override
    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiredAfter()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenExpired(String token) {
        return getTokenExpiration(token).before(new Date());
    }

    @Override
    public boolean isTokenValid(String token, User user){
        final String userEmail = getUserEmailFromToken(token);
        return userEmail.equals(user.getEmail()) && !isTokenExpired(token);
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
