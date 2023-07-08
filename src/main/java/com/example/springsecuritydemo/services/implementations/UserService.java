package com.example.springsecuritydemo.services.implementations;

import com.example.springsecuritydemo.dto.RegisterRequest;
import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.mappers.UserMapper;
import com.example.springsecuritydemo.repositories.UserRepository;
import com.example.springsecuritydemo.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Getting user by his email
     * @param username the user email
     * */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Transactional
    @Override
    public User create(RegisterRequest registerRequest){
        User user = mapper.fromRegisterRequest(registerRequest);
        return repository.save(user);
    }
}
