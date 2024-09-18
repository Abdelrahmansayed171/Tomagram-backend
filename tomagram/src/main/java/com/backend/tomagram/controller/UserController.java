package com.backend.tomagram.controller;

import com.backend.tomagram.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserAccount(@RequestHeader("Authorization") String authHeader){
        try{
            userService.deleteUser(authHeader);
        } catch (EntityNotFoundException e){
            return
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e){
            return
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("User deleted successfully");
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
