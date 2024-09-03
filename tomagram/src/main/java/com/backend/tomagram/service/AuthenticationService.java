package com.backend.tomagram.service;


import com.backend.tomagram.controller.auth.AuthenticationRequest;
import com.backend.tomagram.controller.auth.AuthenticationResponse;
import com.backend.tomagram.controller.auth.RegisterRequest;
import com.backend.tomagram.controller.exception.UserExistsException;
import com.backend.tomagram.models.users.Role;
import com.backend.tomagram.models.users.User;
import com.backend.tomagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(RegisterRequest request) {

        if(userRepository.findByUsername(request.getUsername()).isPresent()
                || userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new UserExistsException("username or email already exists");
        }
        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Password encoding
                .birthdate(LocalDate.parse(request.getBirthdate(), DateTimeFormatter.ISO_DATE)) // Correctly passing LocalDate
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("User Created successfully");
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate user by its credentials via authenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername((request.getUsername()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
