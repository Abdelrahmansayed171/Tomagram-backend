package com.backend.tomagram.listener;

import com.backend.tomagram.dto.PostRequest;
import com.backend.tomagram.dto.SeenPostRequest;
import com.backend.tomagram.dto.UploadRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FeedEventListener {
    @KafkaListener(topics = "tomagram-feed", groupId = "groupId")
    public void handleAddFeed(UploadRequest request) {
        // Process the UploadRequest
        System.out.println("Received UploadRequest: " + request);
        // Add your business logic here
    }

    @KafkaListener(topics = "tomagram-update", groupId = "groupId")
    public void handleUpdatePost(PostRequest request) {
        // Process the PostRequest
        System.out.println("Received PostRequest: " + request);
        // Add your business logic here
    }

    @KafkaListener(topics = "tomagram-seen", groupId = "groupId")
    public void handleSeenPost(SeenPostRequest request) {
        // Process the SeenPostRequest
        System.out.println("Received SeenPostRequest: " + request);
        // Add your business logic here
    }
}
