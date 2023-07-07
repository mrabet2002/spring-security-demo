package com.example.springsecuritydemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtConfig {
    private String secretKey;
    private int expiredAfter;
}
