package com.backend.feedservice.service;

import com.backend.feedservice.dto.PostRequest;
import com.netflix.discovery.provider.Serializer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FeedService {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

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

        // Step 1: Retrieve all post IDs from user's feed (Sorted Set)
        Set<String> postIds = stringRedisTemplate.opsForZSet().range(userSortedSet,0,-1);

        // Step 2: Retrieve All Seen post IDs from user's seen set
        Set<String> seenPostIds = stringRedisTemplate.opsForSet().members(userSeenSetKey);

        // Step 3: (postIds - seenPostIds) - we're going to get difference between 2 sets

        assert postIds != null;
        List<String> filteredPostIds = new ArrayList<>(postIds);
        assert seenPostIds != null;
        filteredPostIds = filteredPostIds.stream()
                .filter( postId -> !seenPostIds.contains(postId))
                .collect(Collectors.toList());

        // reverse List to obtain more recent posts at the top
        Collections.reverse(filteredPostIds);

        // Step 4: retrieve post hashes which ids are included in postIds set
        List<Map<Object, Object>> userPosts = new ArrayList<>();
        for(String postId : filteredPostIds){
            String postKey = "posts:"+postId;
            Map<Object,Object> post = redisTemplate.opsForHash().entries(postKey);
            post.put("id", postId);
            userPosts.add(post);
        }

        return userPosts;
    }
}
