package com.backend.feedservice.service;

import com.backend.feedservice.dto.PostRequest;
import com.backend.feedservice.dto.UploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PostService postService;

    /*
     * Computes a user's feed by retrieving all posts in the user's sorted set,
     * excluding those that have already been seen by the user.
     *
     * @param username the username of the user for whom the feed is being generated
     * @return a list of post hashes (each represented as a Map of fields)
     */
    public List<Map<Object, Object>> getFeedForUser(String username){
        String userSortedSet = "user:" +username;
        String userSeenSetKey = "user:seen:" + username;

        // Step 1: (postIds - seenPostIds) - we're going to get difference between 2 sets
        Set<String> unseenPostIds = stringRedisTemplate.opsForZSet().difference(userSortedSet, userSeenSetKey);
        assert unseenPostIds != null;

        // store postIds in a list to maintain its order through further operations
        List<String> filteredPostIds = new ArrayList<>(unseenPostIds);


        // Step 2: Open only one Redis Database Connection, retrieving all posts
        List<String> postHashKeys = filteredPostIds.stream()
                .map(postId -> "posts:"+postId)
                .toList();
        List<Object> redisPipeline = redisTemplate.executePipelined(
                (RedisCallback<Object>) connection -> {
                    for (String postHashKey : postHashKeys){
                        connection.hashCommands().hGetAll(postHashKey.getBytes());
                    }
                    return null;
                });

        // Step 3: Type-cast pipeline result and add postId to each post map
        List<Map<Object, Object>> userPosts = new ArrayList<>();
        for (int i = redisPipeline.size()-1; i >= 0 ; i--) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> post = (Map<Object, Object>) redisPipeline.get(i);
            if (post != null) {
                post.put("id", filteredPostIds.get(i));
                userPosts.add(post);
            }
        }

        return userPosts;
    }

    /*
     * Add new post Hash to redis cache
     * Add post id to followers sorted set
     *
     * @param UploadRequest contains post details, follower usernames to update their feeds
     */
    public void fanout(UploadRequest request){
        PostRequest postRequest = request.getPostRequest();
        List<String> followers = request.getUserFollowers().getFollowers();
        postService.storePost(postRequest);
        for(String follower : followers){
            postService.addPostToUserFeed(follower, postRequest.getId(), postRequest.getCreatedAt());
        }
    }

}
