package com.backend.tomagram.controller;

import com.backend.tomagram.dto.FollowRequest;
import com.backend.tomagram.service.FollowsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/follows")
public class FollowsController {
    private final FollowsService followsService;

    @PostMapping
    public ResponseEntity<String> follow(@RequestHeader("Authorization") String authHeader, @RequestBody FollowRequest followRequest){
        try{
            followsService.addNewFollowing(authHeader, followRequest.getFollowedUsername());
        } catch (UsernameNotFoundException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body( e.getMessage());
        }
        return ResponseEntity.ok("Following added successfully");
    }
}
