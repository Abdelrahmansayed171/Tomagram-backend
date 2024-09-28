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
    @GetMapping("/upload")
    public String postUpload(){
        System.out.println("cómo estás, amigo");
        return "cómo estás, amigo";
    }
}
