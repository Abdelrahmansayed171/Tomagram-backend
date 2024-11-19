/*
package com.backend.tomagram.service;

import com.backend.tomagram.dto.PostRequest;
import com.backend.tomagram.dto.SeenPostRequest;
import com.backend.tomagram.dto.UploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void addFeed(UploadRequest request) {
        // Send the UploadRequest object to Kafka
        kafkaTemplate.send("tomagram-feed", request);
    }

    public void updatePost(PostRequest request) {z
        // Send the PostRequest object to Kafka
        kafkaTemplate.send("tomagram-update", request);
    }

    public void addPostToSeen(SeenPostRequest request) {
        // Send the SeenPostRequest object to Kafka
        kafkaTemplate.send("tomagram-seen", request);
    }
}
*/
