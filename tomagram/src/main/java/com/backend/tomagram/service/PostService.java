package com.backend.tomagram.service;

import com.backend.tomagram.dto.*;
//import com.backend.tomagram.feign.FeedServiceInterface;
import com.backend.tomagram.models.Follows;
import com.backend.tomagram.models.posts.Post;
import com.backend.tomagram.models.users.User;
import com.backend.tomagram.repository.FollowsRepository;
import com.backend.tomagram.repository.PostRepository;
import com.backend.tomagram.util.JwtUtil;
import com.backend.tomagram.util.TimeUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;
    private final PostRepository postRepository;
//    private final FeedServiceInterface feedInterface;
    private final FollowsRepository followsRepository;
    private final IndexingService indexingService;

    private void fanout(User user, Post post) {
        // create new PostRequest with complete fields (created from db object)
        PostRequest postRequest = PostRequest.builder()
                .id(String.valueOf(post.getId()))
                .username(user.getUsername())
                .content(post.getContent())
                .location(post.getLocation())
                .createdAt(TimeUtil.toCustomString(post.getCreatedAt()))
                .build();

        // get user followers
        List<Follows> followersList = followsRepository.findByFollowed(user);
        UserFollowers userFollowers = new UserFollowers();
        userFollowers.setFollowers(
                followersList.stream()
                        .map(follows -> follows.getFollower().getUsername())
                        .toList()
        );
        UploadRequest uploadRequest = new UploadRequest(postRequest, userFollowers);

        // call FEED-SERVICE method to make fanout functionality using redis
//        feedInterface.addFeed(uploadRequest);
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


        try {
            postRepository.save(post);
            // If mediaUrls are present, call the addMediaUrlsToPost method
            if (postRequest.getMediaUrls() != null && !postRequest.getMediaUrls().isEmpty()) {
                addMediaUrlsToPost(post.getId(), postRequest.getMediaUrls());
            }
            indexingService.indexAllPosts();
        } catch (Exception e) {
            throw new RuntimeException("Error Uploading post");
        }
        fanout(user, post);
    }

    public void addMediaUrlsToPost(Long postId, List<String> mediaUrls) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found with ID: " + postId));
        post.getMediaUrls().addAll(mediaUrls);  // Add new media URLs
        postRepository.save(post);  // Save the post entity
    }

    public void update(String authHeader, PostUpdateRequest postUpdateRequest) throws AccessDeniedException {
        final String jwt = jwtUtil.getJwt(authHeader);
        final String username = jwtService.extractUsername(jwt);

        var post = postRepository.findById(postUpdateRequest.getId()).orElseThrow(() -> new EntityNotFoundException("Post does not exist"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("unauthorized");
        }

        if (postUpdateRequest.getContent() != null) {
            post.setContent(postUpdateRequest.getContent());
        }

        if (postUpdateRequest.getLocation() != null) {
            post.setLocation(postUpdateRequest.getLocation());
        }

        try {
            postRepository.save(post);
        } catch (Exception e) {
            throw new RuntimeException("Error Updating post: " + e.getMessage());
        }
        PostRequest postRequest = PostRequest.builder()
                .id(String.valueOf(post.getId()))
                .location(post.getLocation())
                .content(post.getContent())
                .username(username)
                .createdAt(TimeUtil.toCustomString(post.getCreatedAt()))
                .build();
//        feedInterface.updatePost(postRequest);
    }

    public void deletePost(Long id) {
        postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("post Not found"));
        postRepository.deleteById(id);
    }


    public Post getPost(Long id) throws EntityNotFoundException {
        return postRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Post Not Found"));
    }


    public List<UserPostsResponse> getUserPosts(String username) {
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
