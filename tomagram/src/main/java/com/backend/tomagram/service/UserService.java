package com.backend.tomagram.service;

import com.backend.tomagram.models.users.Role;
import com.backend.tomagram.models.users.User;
import com.backend.tomagram.repository.UserRepository;
import com.backend.tomagram.util.JwtUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class UserService {
    private static
    UserRepository userRepo;

    private final
    JwtService jwtService;

    private final
    JwtUtil jwtUtil;
    private final
    PasswordEncoder passwordEncoder;

    private final
    UserRepository userRepository;


    public UserService(UserRepository userRepo, JwtService jwtService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        UserService.userRepo = userRepo;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public  ResponseEntity<String> updateBio(String authHeader, String bio){
        String jwt = jwtUtil.getJwt(authHeader);
        String username = jwtService.extractUsername(jwt);
        return userRepo.findByUsername(username)
                .map(user -> {
                    user.setBio(bio);
                    userRepo.save(user);
                    return ResponseEntity.ok(user.getBio());
                }).orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found"));
    }

    public ResponseEntity<String> getBio(String username){
        return userRepo.findByUsername(username)
                .map(user -> ResponseEntity.ok(user.getBio()))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found"));
    }

    public void createUser(String name, String username,
                           String email, String pass,
                           String birthdate){
        if(userExists(username)){
            throw new DuplicateKeyException("User with username " + username + " already exists.");
        }
        var user = User.builder()
                .name(name)
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(pass)) // Password encoding
                .birthdate(LocalDate.parse(birthdate, DateTimeFormatter.ISO_DATE)) // Correctly passing LocalDate
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public boolean userExists(String username){
        return userRepo.existsById(username);
    }

    public static User findUser(String username){
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username or Password is not valid"));
    }

}
