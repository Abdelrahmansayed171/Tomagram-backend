package com.backend.feedservice.service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CacheCleanupService {
    private final StringRedisTemplate stringRedisTemplate;

    /*
     * This method is scheduled to run every 8 minutes.
     * It checks for all user:<username> sorted sets and user:seen:<username> sets,
     * and removes members (post_ids) that no longer have a corresponding hash (post:<post_id>).
     */
     // runs every 8 minutes
    @Scheduled(cron = "0 0/8 * * * *")
    public void cleanExpiredPosts(){
        // Step 1: get all keys for those redis sets
        Set<String> userSortedSetKeys = stringRedisTemplate.keys("user:*");
        Set<String> userSeenSetKeys = stringRedisTemplate.keys("user:seen:*");

        // Step 2: Clean user sorted sets
        if (userSortedSetKeys != null && !userSortedSetKeys.isEmpty()) {
            for (String userSortedSetKey : userSortedSetKeys) {
                // Get all post IDs in the sorted set
                Set<String> postIds = stringRedisTemplate.opsForZSet().range(userSortedSetKey, 0, -1);
                if (postIds != null) {
                    for (String postId : postIds) {
                        // Check if the post hash (posts:<postId>) exists
                        String postKey = "posts:" + postId;
                        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(postKey))) {
                            // If the post hash does not exist, remove it from the sorted set
                            stringRedisTemplate.opsForZSet().remove(userSortedSetKey, postId);
                            System.out.println("Removed expired post " + postId + " from sorted set " + userSortedSetKey);
                        }
                    }
                }
            }
        }

        // Step 3: Clean user seen sets
        if (userSeenSetKeys != null && !userSeenSetKeys.isEmpty()) {
            for (String userSeenSetKey : userSeenSetKeys) {
                // Get all post IDs in the seen set
                Set<String> postIds = stringRedisTemplate.opsForSet().members(userSeenSetKey);
                if (postIds != null) {
                    for (String postId : postIds) {
                        // Check if the post hash (posts:<postId>) exists
                        String postKey = "posts:" + postId;
                        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(postKey))) {
                            // If the post hash does not exist, remove it from the seen set
                            stringRedisTemplate.opsForSet().remove(userSeenSetKey, postId);
                            System.out.println("Removed expired post " + postId + " from seen set " + userSeenSetKey);
                        }
                    }
                }
            }
        }
    }
}
