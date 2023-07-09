package com.example.springsecuritydemo.schedulers;

import com.example.springsecuritydemo.services.interfaces.IJwtService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class KeyGenScheduler {

    private final IJwtService jwtService;

    /**
     * Execute the generateSecretKeys() method for the first time when starting the server
     * */
    @PostConstruct
    public void generateSecretKeysOnStartup() throws NoSuchAlgorithmException {
        generateSecretKeys();
    }

    /**
     * Scheduler for generating secret keys
     * and store them to the system environment
     * The secrets will change periodically for more security
     * */
    @Scheduled(cron = "${jwt.scheduler.key-generation-rate}")
    public void generateSecretKeys() throws NoSuchAlgorithmException {
        // Generating the access token
        String accessToken = jwtService.generateSecretKey();
        System.setProperty("access-token-secret", accessToken);
        // Generating the refresh token
        String refreshToken = jwtService.generateSecretKey();
        System.setProperty("refresh-token-secret", refreshToken);
        // Logging the generation date if it's succeeded
        // todo: add log file
        log.info("------> Keys generated successfully at " + (new Date()));
    }
}
