package com.backend.tomagram.controller;

import com.backend.tomagram.repository.UserRepository;
import com.backend.tomagram.service.JwtService;
import com.backend.tomagram.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final
    UserRepository userRepo;

    private final
    JwtService jwtService;

    private final
    JwtUtil jwtUtil;

    public UserController(UserRepository userRepo, JwtService jwtService, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }



    @PutMapping("/bio")
    public ResponseEntity<String> updateUserBio(@RequestHeader("Authorization") String authHeader,
                                                @RequestBody String bio){
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

    @GetMapping("/bio/{username}")
    public ResponseEntity<String> getUserBio(@PathVariable String username){
        return userRepo.findByUsername(username)
                .map(user -> ResponseEntity.ok(user.getBio()))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found"));
    }
}
