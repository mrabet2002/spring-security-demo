package com.example.springsecuritydemo.services.interfaces;

import com.example.springsecuritydemo.dto.AuthenticationRequest;
import com.example.springsecuritydemo.dto.AuthenticationResponse;
import com.example.springsecuritydemo.dto.RefreshTokenRequest;
import com.example.springsecuritydemo.dto.RegisterRequest;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request);
}
