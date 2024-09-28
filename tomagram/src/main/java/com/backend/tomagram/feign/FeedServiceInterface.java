package com.backend.tomagram.feign;

import com.backend.tomagram.dto.UploadRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("FEED-SERVICE")
public interface FeedServiceInterface {
    @PostMapping("/api/fanout/upload")
    public void postUpload(UploadRequest request);
}
