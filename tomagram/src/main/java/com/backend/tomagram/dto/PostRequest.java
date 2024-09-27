package com.backend.tomagram.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String id;
    private String content;
    private String location;
    private String username;
    private String createdAt;
}

