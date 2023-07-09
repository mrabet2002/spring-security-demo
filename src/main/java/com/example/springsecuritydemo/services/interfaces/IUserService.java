package com.example.springsecuritydemo.services.interfaces;

import com.example.springsecuritydemo.dto.RegisterRequest;
import com.example.springsecuritydemo.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface IUserService extends UserDetailsService {
    User loadUserByUsername(String username) throws UsernameNotFoundException;
    @Transactional
    User create(RegisterRequest registerRequest);

    boolean isUserExists(String email);
}
