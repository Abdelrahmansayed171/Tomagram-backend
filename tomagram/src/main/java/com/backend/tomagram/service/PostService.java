package com.backend.tomagram.service;

import com.backend.tomagram.dto.PostRequest;
import com.backend.tomagram.models.posts.Post;
import com.backend.tomagram.repository.PostRepository;
import com.backend.tomagram.util.JwtUtil;
import org.springframework.stereotype.Component;

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
        var post = Post.builder()
                .user(UserService.findUser(username))
                .content(postRequest.getContent())
                .build();
        try{
            postRepository.save(post);
        } catch (Exception e){
            throw new RuntimeException("Error Uploading post");
        }
    }
}
