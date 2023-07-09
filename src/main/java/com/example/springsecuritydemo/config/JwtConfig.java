package com.example.springsecuritydemo.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.Key;


@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    private String accessTokenSecret;
    private String refreshTokenSecret;
    private long accessTokenExpiredAfter;
    private long refreshTokenExpiredAfter;
    private SignatureAlgorithm algorithm;

    public void setAlgorithm(String algorithm) {
        this.algorithm = SignatureAlgorithm.valueOf(algorithm);
    }

    public Key getAccessTokenSecret() {
        byte[] keyBytes = Decoders.BASE64.decode(accessTokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Key getRefreshTokenSecret() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshTokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
