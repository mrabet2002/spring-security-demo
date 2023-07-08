package com.example.springsecuritydemo.web;

import com.example.springsecuritydemo.dto.AuthenticationRequest;
import com.example.springsecuritydemo.dto.AuthenticationResponse;
import com.example.springsecuritydemo.dto.RegisterRequest;
import com.example.springsecuritydemo.services.interfaces.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("trying to authenticate "+request);
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
