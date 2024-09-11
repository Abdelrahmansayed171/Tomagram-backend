package com.backend.tomagram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostsResponse {
    private Long id;
    private String content;
    private String Location;
    private String username;
}
