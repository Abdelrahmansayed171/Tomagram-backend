package com.backend.tomagram.repository;

import com.backend.tomagram.models.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {
    List<Like> findByPostId(long post_id);

    Optional<Like> findByPostIdAndUserId(long postId, String userId);

    void deleteByPostIdAndUserId(long postId, String userId);

}
