package com.backend.tomagram.feign;

import com.backend.tomagram.dto.PostRequest;
import com.backend.tomagram.dto.SeenPostRequest;
import com.backend.tomagram.dto.UploadRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("FEED-SERVICE")
public interface FeedServiceInterface {
    @PostMapping("/api/feed")
    public void addFeed(@RequestBody UploadRequest request);
    @PutMapping("/api/feed")
    public void updatePost(@RequestBody PostRequest request);

    @PostMapping("api/feed/seen")
    public void addPostToSeen(@RequestBody SeenPostRequest request);
}
