package com.backend.tomagram.feign;

import com.backend.tomagram.dto.UploadRequest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("FEED-SERVICE")
public interface FeedServiceInterface {
    @GetMapping("/api/fanout/upload")
    public String postUpload();
}
