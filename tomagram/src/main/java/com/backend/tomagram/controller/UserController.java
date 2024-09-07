package com.backend.tomagram.controller;

import com.backend.tomagram.models.users.User;
import com.backend.tomagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserRepository userRepo;


//    @PostMapping("/bio")
//    public ReponseEntity<String> updateUserBio()

    @GetMapping("/{username}/bio")
    public ResponseEntity<String> getUserBio(@PathVariable String username){
        return userRepo.findByUsername(username)
                .map(user -> ResponseEntity.ok(user.getBio()))
                .orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("User not found"));
    }
}
