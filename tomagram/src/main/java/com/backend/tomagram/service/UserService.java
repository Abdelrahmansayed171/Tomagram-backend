package com.backend.tomagram.service;

import com.backend.tomagram.repository.UserRepository;
import com.backend.tomagram.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final
    UserRepository userRepo;

    private final
    JwtService jwtService;

    private final
    JwtUtil jwtUtil;

    public UserService(UserRepository userRepo, JwtService jwtService, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
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

}
