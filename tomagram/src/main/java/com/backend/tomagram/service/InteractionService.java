package com.backend.tomagram.service;

import com.backend.tomagram.models.Comment;
import com.backend.tomagram.models.Like;
import com.backend.tomagram.repository.CommentRepository;
import com.backend.tomagram.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InteractionService {
    private final CommentRepository commentRepo;
    private final LikeRepository likeRepo;


    /**
     * Add a comment to a post.
     * @param postId the ID of the post
     * @param userId the ID of the user adding the comment
     * @param content the comment content
     * @return the saved Comment
     */
    public Comment addComment(long postId, String userId, String content) {
        Comment comment = Comment.builder()
                .postId(postId)
                .userId(userId)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build();
        return commentRepo.save(comment);
    }

    /**
     * Add a like to a post. If the user already liked the post, it does nothing.
     * @param postId the ID of the post to like
     * @param userId the ID of the user adding the like
     * @return the saved Like
     */
    public Like addLike(long postId, String userId) {
        // Check if the user has already liked the post
        Optional<Like> existingLike = likeRepo.findByPostIdAndUserId(postId, userId);
        if (existingLike.isPresent()) {
            throw new IllegalArgumentException("User has already liked this post.");
        }

        Like like = Like.builder()
                .postId(postId)
                .userId(userId)
                .timestamp(LocalDateTime.now())
                .build();
        return likeRepo.save(like);
    }

    /**
     * Remove a like from a post (unlike).
     * @param postId the ID of the post
     * @param userId the ID of the user removing the like
     */
    public void removeLike(long postId, String userId) {
        likeRepo.deleteByPostIdAndUserId(postId, userId);
    }

    /**
     * Get all comments for a specific post.
     * @param postId the ID of the post
     * @return a list of comments for the post
     */
    public List<Comment> getCommentsForPost(long postId) {
        return commentRepo.findByPostId(postId);
    }

    /**
     * Get all likes for a specific post.
     * @param postId the ID of the post
     * @return a list of likes for the post
     */
    public List<Like> getLikesForPost(long postId) {
        return likeRepo.findByPostId(postId);
    }
}
