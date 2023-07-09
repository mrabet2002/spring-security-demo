package com.example.springsecuritydemo.services.implementations;

import com.example.springsecuritydemo.dto.AuthenticationRequest;
import com.example.springsecuritydemo.dto.AuthenticationResponse;
import com.example.springsecuritydemo.dto.RefreshTokenRequest;
import com.example.springsecuritydemo.dto.RegisterRequest;
import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.services.interfaces.IAuthenticationService;
import com.example.springsecuritydemo.services.interfaces.IJwtService;
import com.example.springsecuritydemo.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registering the user
     * @param request contains the user details
     * @return AuthenticationResponse object (contains the accessToken)
     * */
    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        User user = userService.create(request);
        return generateTokens(user);
    }

    /**
     * Registering the user
     * @param request contains the user credentials
     * @return AuthenticationResponse object (contains the accessToken)
     * */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticating user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        log.info("Authenticated");
        User user = userService.loadUserByUsername(request.getEmail());
        return generateTokens(user);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String newAccessToken = jwtService.generateAccessToken(request.getRefreshToken());
        AuthenticationResponse response = new AuthenticationResponse();
        response.setAccessToken(newAccessToken);
        return response;
    }

    private AuthenticationResponse generateTokens(User user){
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        if (accessToken != null && refreshToken != null)
            return new AuthenticationResponse(accessToken, refreshToken);
        throw new RuntimeException("An error occurred");
    }
}
