package com.example.springsecuritydemo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    /**
     * Invoked when an authentication exception occurs during the authentication process.
     *
     * This method delegates the exception handling to the configured Exception handler (HandlerExceptionResolver),
     * passing the request, response, and authentication exception as parameters.
     *
     * @param request        The HTTP request being processed.
     * @param response       The HTTP response to be sent back to the client.
     * @param authException  The AuthenticationException that occurred during the authentication process.
     * @throws IOException      If an I/O error occurs during the handling of the request.
     * @throws ServletException If any other error occurs during the handling of the request.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        resolver.resolveException(request, response, null, authException);
    }
}
