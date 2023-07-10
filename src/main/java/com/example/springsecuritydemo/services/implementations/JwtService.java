package com.example.springsecuritydemo.services.implementations;

import com.example.springsecuritydemo.config.JwtConfig;
import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.enums.Role;
import com.example.springsecuritydemo.services.interfaces.IJwtService;
import com.example.springsecuritydemo.services.interfaces.IUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService implements IJwtService {

    private final JwtConfig jwtConfig;
    private final IUserService userService;

    @Override
    public String getUserEmail(String token) {
        return getAccessTokenClaims(token).getSubject();
    }

    @Override
    public Role getUserRole(String token) {
        return Role.valueOf(getAccessTokenClaims(token).get("role").toString());
    }

    /**
     * Generate token by passing a user object only
     */
    @Override
    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    /**
     * Generate token
     *
     * @param extraClaims any custom claims to add in the token
     * @param user        the user for whom we generate the token
     */
    @Override
    public String generateToken(Map<String, Object> extraClaims, User user) {
        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setClaims(Map.of("role", user.getRole()))
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpiredAfter()))
                .signWith(jwtConfig.getAccessTokenSecret(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generate token
     *
     * @param extraClaims any custom claims to add in the token
     * @param subject     the token subject
     * @param secret      specifying the secret key used for signing the token
     */
    @Override
    public String generateToken(Map<String, Object> extraClaims, String subject, Key secret, long expiredAfter) {
        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredAfter))
                .signWith(secret, jwtConfig.getAlgorithm())
                .compact();
    }

    @Override
    public String generateAccessToken(String refreshToken) {
        String userEmail = getRefreshTokenClaims(refreshToken).getSubject();
        User user = userService.loadUserByUsername(userEmail);
        return generateAccessToken(user);
    }

    @Override
    public String generateAccessToken(User user) {
        return generateToken(
                Map.of("role", user.getRole()),
                user.getEmail(),
                jwtConfig.getAccessTokenSecret(),
                jwtConfig.getAccessTokenExpiredAfter()
        );
    }

    @Override
    public String generateRefreshToken(User user) {
        return generateToken(
                new HashMap<>(),
                user.getEmail(),
                jwtConfig.getRefreshTokenSecret(),
                jwtConfig.getRefreshTokenExpiredAfter()
        );
    }

    @Override
    public User getUserFromToken(String jwt) {
        User user = new User();
        user.setEmail(getUserEmail(jwt));
        user.setRole(getUserRole(jwt));
        return user;
    }

    @Override
    public String generateSecretKey() throws NoSuchAlgorithmException {
        // Create a KeyGenerator instance for the desired algorithm (e.g., AES, DES, etc.)
        KeyGenerator keyGenerator = KeyGenerator.getInstance(jwtConfig.getAlgorithm().getJcaName());

        // Generate a secure random key
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(secureRandom);

        // Generate the secret key
        SecretKey secretKey = keyGenerator.generateKey();

        // Convert the secret key to a byte array
        byte[] keyBytes = secretKey.getEncoded();

        // Print the secret key as a hexadecimal string
        return bytesToHex(keyBytes);
    }

    // Helper method to get access token claims
    private Claims getAccessTokenClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getAccessTokenSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Helper method to get refresh token claims
    private Claims getRefreshTokenClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getRefreshTokenSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Helper method to convert byte array to hexadecimal string
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
}
