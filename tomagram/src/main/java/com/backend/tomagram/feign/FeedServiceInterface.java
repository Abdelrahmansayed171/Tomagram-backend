package com.backend.tomagram.feign;

import com.backend.tomagram.dto.UploadRequest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("FEED-SERVICE")
public interface FeedServiceInterface {
    @PostMapping("/api/fanout/upload")
    public ResponseEntity<Object> postUpload();
}
