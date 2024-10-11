package com.backend.feedservice.listener;

import com.backend.feedservice.dto.PostRequest;
import com.backend.feedservice.dto.SeenPostRequest;
import com.backend.feedservice.dto.UploadRequest;
import com.backend.feedservice.service.FeedService;
import com.backend.feedservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedEventListener {
    private final FeedService feedService;
    private final PostService postService;

    @KafkaListener(topics = "tomagram-feed", groupId = "groupId")
    public void handleAddFeed(UploadRequest request) {
        // Process the UploadRequest
        System.out.println("Received UploadRequest: " + request);
        feedService.fanout(request);
    }

    @KafkaListener(topics = "tomagram-update", groupId = "groupId")
    public void handleUpdatePost(PostRequest request) {
        // Process the PostRequest
        System.out.println("Received PostRequest: " + request);
        postService.storePost(request);
    }


    @KafkaListener(topics = "tomagram-seen", groupId = "groupId")
    public void handleSeenPost(SeenPostRequest request) {
        // Process the SeenPostRequest
        System.out.println("Received SeenPostRequest: " + request);
        feedService.markPostsAsSeen(request);
    }
}
