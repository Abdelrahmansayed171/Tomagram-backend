package com.backend.tomagram.service;

import com.backend.tomagram.repository.FollowsRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
@RequiredArgsConstructor
@Service
public class FollowsService {
    private final FollowsRepository followsRepository;

    public void addNewFollowing(String authHeader, String followedUsername) {

    }
}
