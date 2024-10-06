package com.backend.tomagram.repository;

import com.backend.tomagram.models.Comment;
import com.backend.tomagram.models.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(long postId);
    Optional<Comment> findByPostIdAndUserId(long postId, String userId);

    void deleteByPostIdAndUserId(long postId, String userId);
}
