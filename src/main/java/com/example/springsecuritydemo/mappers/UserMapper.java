package com.example.springsecuritydemo.mappers;

import com.example.springsecuritydemo.dto.RegisterRequest;
import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * User mapper for mapping
 * used dependency <mapstruct>
 * */
@Mapper(componentModel = "spring")
public interface UserMapper {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Role roleUser = Role.USER;

    /**
     * Mapping from RegisterRequest to User
     * The role field will be user by default
     * The password will be encoded
     * before mapping it to the returned User object
     * */
    @Mapping(target = "role", expression = "java(roleUser)")
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registerRequest.getPassword()))")
    User fromRegisterRequest(RegisterRequest registerRequest);
}
