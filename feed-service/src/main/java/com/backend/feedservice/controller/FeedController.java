package com.backend.feedservice.controller;

import com.backend.feedservice.dto.PostRequest;
import com.backend.feedservice.dto.UploadRequest;
import com.backend.feedservice.service.FeedService;
import com.backend.feedservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {
    private final FeedService feedService;
    private final PostService postService;

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

    /*
     * add new post hash and fanout it into user Followers reflecting it in Redis Cache
     * @param UploadRequest object contains (UserFollowers, PostRequest)
     */
    @PostMapping()
    public void addFeed(@RequestBody UploadRequest request){
        feedService.fanout(request);
    }

    /*
     * Update Post Hash details in the redis, in case post has been updated
     * @param PostRequest object contains post details
     */
    @PutMapping
    public void updatePost(@RequestBody PostRequest request){
        postService.storePost(request);
    }
}
