package com.example.springsecuritydemo.services.implementations;

import com.example.springsecuritydemo.dto.AuthenticationRequest;
import com.example.springsecuritydemo.dto.AuthenticationResponse;
import com.example.springsecuritydemo.dto.RegisterRequest;
import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.services.interfaces.IAuthenticationService;
import com.example.springsecuritydemo.services.interfaces.IJwtService;
import com.example.springsecuritydemo.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = userService.create(request);
        String token = jwtService.generateToken(user);
        if (token != null)
            return new AuthenticationResponse(token);
        throw new RuntimeException("An error occurred");
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(user);
        if (token != null)
            return new AuthenticationResponse(token);
        throw new RuntimeException("An error occurred");
    }
}
