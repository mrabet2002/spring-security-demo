package com.example.springsecuritydemo.services.interfaces;

import com.example.springsecuritydemo.dto.*;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
