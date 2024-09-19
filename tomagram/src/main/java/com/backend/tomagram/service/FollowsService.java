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
import java.util.function.Function;
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
        return getFollows(username,followsRepository::findByFollowed,1);
    }

    public List<FollowsResponse> getFollowings(String username) {
        return getFollows(username,followsRepository::findByFollower,2);
    }

    private List<FollowsResponse> getFollows(String username,
                                             Function<User, List<Follows>> findFollowsMethod,
                                             Integer followType) {
        User user = UserService.findUser(username);
        if(user == null)
            throw new UsernameNotFoundException("user not found with username: " + username);

        List<FollowsResponse> resList;

        try {
            List<Follows> followsList = findFollowsMethod.apply(user);
            resList =  followsList.stream()
                    .map(follows -> new FollowsResponse(
                            followType.equals(1) ?
                                    follows.getFollower().getUsername()
                                    : follows.getFollowed().getUsername()
                            , follows.getFollowedAt()))
                    .collect(Collectors.toList());
        } catch (Exception e){
            System.err.println("An Unexpected error occurred while retrieving followers: " + e.getMessage());
            throw e;
        }
        return resList;
    }
}
