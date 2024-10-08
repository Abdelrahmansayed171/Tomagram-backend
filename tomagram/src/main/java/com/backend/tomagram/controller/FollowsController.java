package com.backend.tomagram.controller;

import com.backend.tomagram.dto.FollowRequest;
import com.backend.tomagram.dto.FollowsResponse;
import com.backend.tomagram.service.FollowsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Following added successfully");
    }

    @GetMapping("/{username}/followers")
    public List<FollowsResponse> getFollowers(@PathVariable String username){
        return followsService.getFollowers(username);
    }

    @GetMapping("/{username}/followings")
    public List<FollowsResponse> getFollowings(@PathVariable String username){
        return followsService.getFollowings(username);
    }

    // Exception handler for UserNotFoundException
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleUserNotFoundException(UsernameNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // General Exception Handler
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Return 500 status for general exceptions
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
