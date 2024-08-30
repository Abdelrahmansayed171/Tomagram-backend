package com.backend.tomagram.config.filter;

import com.backend.tomagram.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor // build constructor with any final field defined inside within the class
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        /*
        Lombok @NonNull annotation boilerplate code
        if (request == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        */

        // we need to pass JWT to the header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response); // pass the request, response to the next filter (middleware)
            return;
        }

        jwt = authHeader.substring(7);
        userName =  jwtService.extractUsername(jwt); // extract userEmail from JWT token;

    }
}
