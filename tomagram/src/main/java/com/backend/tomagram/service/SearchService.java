package com.backend.tomagram.service;

import com.backend.tomagram.models.CommentDocument;
import com.backend.tomagram.models.PostDocument;
import com.backend.tomagram.repository.CommentSearchRepository;
import com.backend.tomagram.repository.PostSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final PostSearchRepository postSearchRepository;
    private final CommentSearchRepository commentSearchRepository;

    // Search posts by content or location
    public List<PostDocument> searchPostsByContent(String keyword) {
        List<PostDocument> posts = postSearchRepository.findByContentContaining(keyword);
        posts.addAll(postSearchRepository.findByLocation(keyword));
        return posts;
    }

    // Search comments by content
    public List<CommentDocument> searchCommentsByContent(String keyword) {
        return commentSearchRepository.findByContentContaining(keyword);
    }
}
