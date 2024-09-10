package com.backend.tomagram.controller;

import com.backend.tomagram.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PutMapping("/bio")
    public ResponseEntity<String> updateUserBio(@RequestHeader("Authorization") String authHeader,
                                                @RequestParam("bio") String bio){

        return userService.updateBio(authHeader, bio);
    }

    @GetMapping("/bio/{username}")
    public ResponseEntity<String> getUserBio(@PathVariable String username){
        return userService.getBio(username);
    }
}
