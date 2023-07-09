package com.example.springsecuritydemo.exceptions;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException() {
        super("username already taken!");
    }
}
