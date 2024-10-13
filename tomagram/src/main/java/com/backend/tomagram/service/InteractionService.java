//package com.backend.tomagram.service;
//
//import com.backend.tomagram.dto.InteractionRequest;
//import com.backend.tomagram.models.Comment;
//import com.backend.tomagram.models.Like;
//import com.backend.tomagram.repository.CommentRepository;
//import com.backend.tomagram.repository.LikeRepository;
//import com.backend.tomagram.util.JwtUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Service
//public class InteractionService {
//    private final CommentRepository commentRepo;
//    private final LikeRepository likeRepo;
//    private final JwtUtil jwtUtil;
//    private final JwtService jwtService;
//    private final IndexingService indexingService;
//
//    /**
//     * Add a comment to a post.
//     * @param authHeader the authorization header carrying JWT
//     * @param interactionRequest the post_id and content of the comment
//     * @return the saved Comment
//     */
//    public Comment addComment(String authHeader, InteractionRequest interactionRequest) {
//        final String jwt = jwtUtil.getJwt(authHeader);
//        final String username = jwtService.extractUsername(jwt);
//        Comment comment = Comment.builder()
//                .postId(interactionRequest.getPostId())
//                .userId(username)
//                .content(interactionRequest.getContent())
//                .timestamp(LocalDateTime.now())
//                .build();
//        try {
//            commentRepo.save(comment);
//            indexingService.indexAllComments();
//        } catch (Exception e){
//            throw new RuntimeException("Error Saving new Comment or update indexing elastic search");
//        }
//        return comment;
//    }
//
//    /**
//     * Add a like to a post. If the user already liked the post, it does nothing.
//     * @param authHeader the authorization header carrying JWT
//     * @param interactionRequest the post_id and content of the comment
//     * @return the saved Like
//     */
//    public Like addLike(String authHeader, InteractionRequest interactionRequest) {
//        final String jwt = jwtUtil.getJwt(authHeader);
//        final String username = jwtService.extractUsername(jwt);
//
//        // Check if the user has already liked the post
//        Optional<Like> existingLike = likeRepo.findByPostIdAndUserId(interactionRequest.getPostId(), username);
//        if (existingLike.isPresent()) {
//            throw new IllegalArgumentException("User has already liked this post.");
//        }
//
//        Like like = Like.builder()
//                .postId(interactionRequest.getPostId())
//                .userId(username)
//                .timestamp(LocalDateTime.now())
//                .build();
//        return likeRepo.save(like);
//    }
//
//    /**
//     * Remove a like from a post (unlike).
//     * @param authHeader the authorization header carrying JWT
//     * @param interactionRequest the post_id and content of the comment
//     */
//    public void removeLike(String authHeader, InteractionRequest interactionRequest) {
//        final String jwt = jwtUtil.getJwt(authHeader);
//        final String username = jwtService.extractUsername(jwt);
//        likeRepo.deleteByPostIdAndUserId(interactionRequest.getPostId(), username);
//    }
//
//    /**
//     * Get all comments for a specific post.
//     * @param postId the ID of the post
//     * @return a list of comments for the post
//     */
//    public List<Comment> getCommentsForPost(long postId) {
//        return commentRepo.findByPostId(postId);
//    }
//
//    /**
//     * Get all likes for a specific post.
//     * @param postId the ID of the post
//     * @return a list of likes for the post
//     */
//    public List<Like> getLikesForPost(long postId) {
//        return likeRepo.findByPostId(postId);
//    }
//}
