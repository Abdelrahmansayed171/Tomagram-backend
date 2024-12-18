package com.backend.tomagram.controller;

import com.backend.tomagram.dto.PostRequest;
import com.backend.tomagram.dto.PostUpdateRequest;
import com.backend.tomagram.dto.UserPostsResponse;
//import com.backend.tomagram.feign.FeedServiceInterface;
import com.backend.tomagram.models.posts.Post;
import com.backend.tomagram.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> addPost(@RequestHeader("Authorization") String authHeader, @RequestBody PostRequest postRequest){
        try {
            postService.upload(authHeader, postRequest);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok("Post added Successfully");
    }

    @PutMapping
    public ResponseEntity<String> updatePost(@RequestHeader("Authorization") String authHeader, @RequestBody PostUpdateRequest postUpdateRequest){
        try {
            postService.update(authHeader, postUpdateRequest);
        } catch (AccessDeniedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok("Post updated Successfully");
    }

    @GetMapping
    public Post getPost(@RequestParam("id") Long id){
        return postService.getPost(id);
    }

    @GetMapping("/user")
    public List<UserPostsResponse> getPosts(@RequestParam("usr") String username){
        return postService.getUserPosts(username);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePost(@RequestParam("id") Long id){
        try{
            postService.deletePost(id);
            return ResponseEntity.ok("Post Deleted successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
