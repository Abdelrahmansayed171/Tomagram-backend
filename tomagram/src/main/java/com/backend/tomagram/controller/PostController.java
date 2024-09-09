package com.backend.tomagram.controller;

import com.backend.tomagram.dto.PostRequest;
import com.backend.tomagram.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<String> addPost(@RequestHeader("Authorization") String authHeader, @RequestBody PostRequest postRequest){
        try {
            postService.upload(authHeader, postRequest);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok("Post added Successfully");
    }

}
