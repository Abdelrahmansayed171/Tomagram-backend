package com.backend.feedservice.service;

import com.backend.feedservice.dto.PostRequest;
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
        long createdAtUnixTimestamp = TimeUtil.stringToUnixTimestamp(createdAt);

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

    public void storePost(PostRequest postRequest){
        String hashKey = "posts:" + postRequest.getId();
        long createdAtUnixTimestamp = TimeUtil.stringToUnixTimestamp(postRequest.getCreatedAt());

        Map<String, Object> postFields = Map.of(
                "content", postRequest.getContent(),
                "createdAt", createdAtUnixTimestamp,
                "location", postRequest.getLocation(),
                "username", postRequest.getUsername()
        );

        redisTemplate.opsForHash().putAll(hashKey, postFields);
        // set expiration time
        redisTemplate.expire(hashKey, Duration.ofSeconds(EXPIRATION_TIME));
    }

    /*
     * Add post ID to user's sorted set (feed)
     * @param username
     * @param postId
     * @param timestamp which considered to be sorted set score
     */
    public void addPostToUserFeed(String username, String postId, String createdAt){
        String sortedSetKey = "user:" + username;
        long createdAtUnixTimestamp = TimeUtil.stringToUnixTimestamp(createdAt);

        redisTemplate.opsForZSet().add(sortedSetKey, postId, createdAtUnixTimestamp);
    }

    /*
     * add postId to user's seen posts
     * @param username
     * @param postId
     */
    public void markPostAsSeen(String username, String postId){
        String setKey = "user:seen:" + username;

        redisTemplate.opsForSet().add(setKey, postId);
    }


}
