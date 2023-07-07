package com.example.springsecuritydemo.security;

import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.services.interfaces.IJwtService;
import com.example.springsecuritydemo.services.interfaces.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** JwtAuthenticationFilter is the first thing that will receive the request
 *  It extends {@link OncePerRequestFilter}
 *  witch is a filter for every coming request
 *  So ore filter will be active for every request send by the client
 * */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final IUserService userService;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Getting the token from the received request
        final String authHeader = request.getHeader("Authorization");
        final String userEmail;
        // Checking if "Authorization" header contains a bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            // Chaining with the next filter
            filterChain.doFilter(request, response);
            return;
        }
        // Extracting the token
        String jwt = authHeader.substring(7);
        // Checking if the user isn't authenticated
        userEmail = jwtService.getUserEmailFromToken(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.loadUserByUsername(userEmail);
            // Checking if the token is valid
            if (jwtService.isTokenValid(jwt, user)) {
                // Authenticate the request by setting the security context
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
