package com.backend.feedservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadRequest {
    private PostRequest postRequest;
    private UserFollowers userFollowers;
}
