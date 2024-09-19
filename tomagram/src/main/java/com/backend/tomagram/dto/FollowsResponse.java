package com.backend.tomagram.dto;

import com.backend.tomagram.models.users.User;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowsResponse {
    private String userName;
    private LocalDateTime followed_At;

    public FollowsResponse(User user, LocalDateTime followedAt) {
    }
}
