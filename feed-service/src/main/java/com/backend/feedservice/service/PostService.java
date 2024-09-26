package com.backend.feedservice.service;

import com.backend.feedservice.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PostService {
    private static final long EXPIRATION_TIME = 30 * 24 * 60 * 60; // 30 days in seconds

    private final RedisTemplate<String, Object> redisTemplate;

    /*
    * Stores a post with expiration of 30 days
    *
    * @param postId the ID of the post from tomagram-service database
    * @param content the content of the post
    * @param createdAt creation timestamp
    * @param location the location where post created
    * @param username the creator of the post
    */
    public void storePost(String postId, String content, String createdAt, String location, String username){
        String hashKey = "posts:" + postId;
        LocalDateTime dateTime = TimeUtil.fromString(createdAt);
        long createdAtUnixTimestamp = TimeUtil.toUnixTimestamp(dateTime);

        Map<String, Object> postFields = Map.of(
                "content", content,
                "createdAt", createdAtUnixTimestamp,
                "location", location,
                "username", username
        );

        redisTemplate.opsForHash().putAll(hashKey, postFields);
        // set expiration time
        redisTemplate.expire(hashKey, Duration.ofSeconds(EXPIRATION_TIME));
    }


}
