package com.backend.tomagram.service;

import com.backend.tomagram.models.Follows;
import com.backend.tomagram.models.users.User;
import com.backend.tomagram.repository.FollowsRepository;
import com.backend.tomagram.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
            var follows = Follows.builder()
                    .follower(followerUser)
                    .followed(followedUser)
                    .build();
            followsRepository.save(follows);
        } catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("User does not exists!");
        } catch (Exception e){
            throw new RuntimeException("Error add new following");
        }
    }
}
