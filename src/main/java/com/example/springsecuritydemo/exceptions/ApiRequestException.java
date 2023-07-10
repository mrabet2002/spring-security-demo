package com.example.springsecuritydemo.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
/**
 * A simple Runtime exception with an http status code field
 * */
@Data
public class ApiRequestException extends RuntimeException {
    private HttpStatus status;
    public ApiRequestException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

}
