package com.backend.feedservice.controller;

import com.backend.feedservice.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {
    private final FeedService feedService;

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserFeed(@PathVariable String username){
        List<Map<Object, Object>> feed;
        try {
            feed = feedService.getFeedForUser(username);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Couldn't get user feed");
        }
        return ResponseEntity.ok(feed);
    }
}
