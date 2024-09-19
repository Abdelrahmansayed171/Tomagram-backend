package com.backend.tomagram.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowsResponse {
    private String userName;
    private LocalDateTime followed_At;
}
