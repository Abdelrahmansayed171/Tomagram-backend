package com.backend.tomagram.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String id;
    private String content;
    private String location;
    private String username;
    private List<String> mediaUrls;
    private String createdAt;
}

