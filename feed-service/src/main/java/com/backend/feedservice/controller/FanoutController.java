package com.backend.feedservice.controller;

import com.backend.feedservice.dto.UploadRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fanout")
public class FanoutController {
    @PostMapping("/upload")
    public void postUpload(UploadRequest request){
        System.out.println("cómo estás, amigo");
    }

}
