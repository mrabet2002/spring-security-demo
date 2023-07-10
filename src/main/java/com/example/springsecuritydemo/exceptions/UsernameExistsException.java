package com.example.springsecuritydemo.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameExistsException extends ApiRequestException {
    public UsernameExistsException() {
        super("username already taken!", HttpStatus.valueOf(409));
    }
}
