package com.backend.tomagram.service;

import com.backend.tomagram.dto.FollowsResponse;
import com.backend.tomagram.models.Follows;
import com.backend.tomagram.models.FollowsId;
import com.backend.tomagram.models.users.User;
import com.backend.tomagram.repository.FollowsRepository;
import com.backend.tomagram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FollowsService {
    private final FollowsRepository followsRepository;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    public void addNewFollowing(String authHeader, String followedUsername) {
        try{
            String jwt = jwtUtil.getJwt(authHeader);
            String username = jwtService.extractUsername(jwt);
            User followerUser = UserService.findUser(username);
            User followedUser = UserService.findUser(followedUsername);
            FollowsId followsId = FollowsId.builder()
                    .followedUsername(followedUsername)
                    .followerUsername(username)
                    .build();
            var follows = Follows.builder()
                    .id(followsId)
                    .follower(followerUser)
                    .followed(followedUser)
                    .build();
            followsRepository.save(follows);
        } catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("User does not exists!");
        } catch (Exception e){
            throw new RuntimeException("Error add new following", e);
        }
    }

    public List<FollowsResponse> getFollowers(String username) {
        List<FollowsResponse> responseList = new ArrayList<>();
        try {
            User followedUser = UserService.findUser(username);
            if (followedUser == null) {
                throw new UsernameNotFoundException("user not found with username: " + username);
            }
            List<Follows> followers = followsRepository.findByFollowed(followedUser);

            responseList = followers.stream()
                    .map(follows ->
                            new FollowsResponse(follows.getFollower().getUsername(),
                                    follows.getFollowedAt()))
                    .collect(Collectors.toList());
        } catch (Exception e){
            System.err.println("An Unexpected error occurred while retrieving followers: " + e.getMessage());
        }
        return responseList;
    }

    public List<FollowsResponse> getFollowings(String username) {
        List<FollowsResponse> responseList = new ArrayList<>();
        try {
            User followerUser = UserService.findUser(username);
            if (followerUser == null) {
                throw new UsernameNotFoundException("user not found with username: " + username);
            }
            List<Follows> followers = followsRepository.findByFollower(followerUser);

            responseList = followers.stream()
                    .map(follows ->
                            new FollowsResponse(follows.getFollower().getUsername(),
                                    follows.getFollowedAt()))
                    .collect(Collectors.toList());
        } catch (Exception e){
            System.err.println("An Unexpected error occurred while retrieving followers: " + e.getMessage());
        }
        return responseList;
    }
}
