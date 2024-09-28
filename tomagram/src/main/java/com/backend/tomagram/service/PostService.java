package com.backend.tomagram.service;

import com.backend.tomagram.dto.PostRequest;
import com.backend.tomagram.dto.PostUpdateRequest;
import com.backend.tomagram.dto.UserPostsResponse;
import com.backend.tomagram.feign.FeedServiceInterface;
import com.backend.tomagram.models.posts.Post;
import com.backend.tomagram.models.users.User;
import com.backend.tomagram.repository.PostRepository;
import com.backend.tomagram.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final PostRepository postRepository;
    private final FeedServiceInterface feedInterface;

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
        }

        if(postUpdateRequest.getLocation() != null){
            post.setLocation(postUpdateRequest.getLocation());
        }

        try {
            postRepository.save(post);
        } catch (Exception e){
            throw new RuntimeException("Error Updating post: " + e.getMessage());
        }
    }

    public void deletePost(Long id){
        postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("post Not found"));
        postRepository.deleteById(id);
    }



    public Post getPost(Long id) throws EntityNotFoundException{
        return postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post Not Found"));
    }


    public List<UserPostsResponse> getUserPosts(String username){
        User user = UserService.findUser(username);
        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(post -> UserPostsResponse.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .Location(post.getLocation())
                    .username(post.getUser().getUsername())
                    .build()).toList();
    }

}
