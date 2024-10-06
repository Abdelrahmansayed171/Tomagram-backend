package com.backend.tomagram.repository;

import com.backend.tomagram.models.Like;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends MongoRepository<Like, String> {

}
