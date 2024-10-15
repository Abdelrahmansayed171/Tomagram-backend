package com.backend.tomagram.repository;

import com.backend.tomagram.models.PostDocument;
import com.backend.tomagram.models.posts.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostSearchRepository extends ElasticsearchRepository<PostDocument, Long> {
    List<PostDocument> findByContentContaining(String content);
    List<PostDocument> findByLocation(String location);

}
