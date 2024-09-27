package com.backend.feedservice.controller;

import com.backend.feedservice.dto.UploadRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fanout")
public class FanoutController {
    @PostMapping("/upload")
    public ResponseEntity<Object> postUpload(@RequestBody UploadRequest request){
        return ResponseEntity.ok("com estas");
    }
}
