package com.example.springsecuritydemo.web;

import com.example.springsecuritydemo.dto.ApiException;
import com.example.springsecuritydemo.exceptions.ApiRequestException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Exception handler for the controller level
 * Customizing exceptions to send a clear
 * and unified response to the client
 * */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<ApiException> handleApiGlobalException(ApiRequestException exception){
        HttpStatus status = exception.getStatus();
        ApiException apiException = new ApiException(
                exception.getMessage(),
                status
        );
        return ResponseEntity.status(status).body(apiException);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<ApiException> handleBadCredentialsException(BadCredentialsException exception){
        ApiException apiException = getUnauthorizedException(exception.getMessage());
        return ResponseEntity.status(apiException.getStatus()).body(apiException);
    }

    @ExceptionHandler(value = {MalformedJwtException.class})
    public ResponseEntity<ApiException> handleMalformedJwtException(MalformedJwtException exception){
        ApiException apiException = getUnauthorizedException(exception.getMessage());
        return ResponseEntity.status(apiException.getStatus()).body(apiException);
    }

    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<ApiException> handleJwtException(JwtException exception){
        ApiException apiException = getUnauthorizedException(exception.getMessage());
        return ResponseEntity.status(apiException.getStatus()).body(apiException);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<ApiException> handleExpiredJwtException(ExpiredJwtException exception){
        ApiException apiException = getUnauthorizedException(exception.getMessage());
        return ResponseEntity.status(apiException.getStatus()).body(apiException);
    }

    @ExceptionHandler(value = {InsufficientAuthenticationException.class})
    public ResponseEntity<ApiException> handleInsufficientAuthenticationException(InsufficientAuthenticationException exception){
        HttpStatus status = HttpStatus.FORBIDDEN;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                status
        );
        return ResponseEntity.status(status).body(apiException);
    }

    private ApiException getUnauthorizedException(String message) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                message,
                status
        );
        return apiException;
    }
}
