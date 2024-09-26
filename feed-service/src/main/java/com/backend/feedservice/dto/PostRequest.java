package com.backend.feedservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    private String id;
    private String content;
    private String location;
    private String username;
    private String createdAt;
}
