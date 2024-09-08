package com.backend.tomagram.controller;

import com.backend.tomagram.dto.BioUpdateRequest;
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
                                                @RequestBody BioUpdateRequest bioRequest){

        return userService.updateBio(authHeader,bioRequest.getBio());
    }

    @GetMapping("/bio/{username}")
    public ResponseEntity<String> getUserBio(@PathVariable String username){
        return userService.getBio(username);
    }
}
