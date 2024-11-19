package com.backend.tomagram.service;

import com.backend.tomagram.models.Comment;
import com.backend.tomagram.models.CommentDocument;
import com.backend.tomagram.models.PostDocument;
import com.backend.tomagram.models.posts.Post;
import com.backend.tomagram.repository.CommentRepository;
//import com.backend.tomagram.repository.CommentSearchRepository;
import com.backend.tomagram.repository.PostRepository;
//import com.backend.tomagram.repository.PostSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class IndexingService {
    private final PostRepository postRepository;  // SQL Repository
    private final CommentRepository commentRepository;  // MongoDB Repository
//    private final PostSearchRepository postSearchRepository;
//    private final CommentSearchRepository commentSearchRepository;

    // Indexing Posts from SQL
    public void indexAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDocument> postDocuments = posts.stream()
                .map(post -> new PostDocument(
                        post.getId(),
                        post.getContent(),
                        post.getLocation(),
                        post.getUser().getUsername(),
                        post.getCreatedAt()))
                .collect(Collectors.toList());

//        postSearchRepository.saveAll(postDocuments);
    }

    // Indexing Comments from MongoDB
    public void indexAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDocument> commentDocuments = comments.stream()
                .map(comment -> new CommentDocument(
                        comment.getId(),
                        comment.getContent(),
                        comment.getPostId(),
                        comment.getUserId(),
                        comment.getTimestamp()))
                .collect(Collectors.toList());

//        commentSearchRepository.saveAll(commentDocuments);
    }

}
