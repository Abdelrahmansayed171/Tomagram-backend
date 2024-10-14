package com.backend.tomagram.controller;

import com.backend.tomagram.dto.BioUpdateRequest;
import com.backend.tomagram.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> updateUserBio(@RequestHeader(value = "Authorization", required = false) String authHeader,
                                                @RequestBody BioUpdateRequest bioUpdateRequest){

        return userService.updateBio(authHeader, bioUpdateRequest.getBio());
    }

    @GetMapping("/bio/{username}")
    public ResponseEntity<String> getUserBio(@PathVariable String username){
        return userService.getBio(username);
    }
}
