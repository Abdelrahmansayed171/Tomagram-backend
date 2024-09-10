package com.backend.tomagram.service;

import com.backend.tomagram.dto.PostRequest;
import com.backend.tomagram.dto.PostUpdateRequest;
import com.backend.tomagram.models.posts.Post;
import com.backend.tomagram.repository.PostRepository;
import com.backend.tomagram.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Component
public class PostService {
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final PostRepository postRepository;
    public PostService(JwtUtil jwtUtil, JwtService jwtService, PostRepository postRepository) {
        this.jwtUtil = jwtUtil;
        this.jwtService = jwtService;
        this.postRepository = postRepository;
    }
    public void upload(String authHeader, PostRequest postRequest) {
        final String jwt = jwtUtil.getJwt(authHeader);
        final String username = jwtService.extractUsername(jwt);
        var user = UserService.findUser(username);
        var post = Post.builder()
                .user(user)
                .content(postRequest.getContent())
                .location(postRequest.getLocation())
                .build();
        try{
            postRepository.save(post);
        } catch (Exception e){
            throw new RuntimeException("Error Uploading post");
        }
    }


    public void update(String authHeader, PostUpdateRequest postUpdateRequest) throws AccessDeniedException {
        final String jwt = jwtUtil.getJwt(authHeader);
        final String username = jwtService.extractUsername(jwt);

        var post = postRepository.findById(postUpdateRequest.getId()).orElseThrow(() -> new EntityNotFoundException("Post does not exist"));

        if(!post.getUser().getUsername().equals(username)){
            throw new AccessDeniedException("unauthorized");
        }

        if(postUpdateRequest.getContent() != null){
            post.setContent(postUpdateRequest.getContent());
            post.setLocation(postUpdateRequest.getLocation());
        }

        try {
            postRepository.save(post);
        } catch (Exception e){
            throw new RuntimeException("Error Updating post: " + e.getMessage());
        }
    }

}
