package com.backend.tomagram.controller;

import com.backend.tomagram.models.CommentDocument;
import com.backend.tomagram.models.PostDocument;
import com.backend.tomagram.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;

    // Endpoint to search posts by content keyword
    @GetMapping("/posts")
    public ResponseEntity<List<PostDocument>> searchPosts(@RequestParam("keyword") String keyword) {
        List<PostDocument> posts = searchService.searchPostsByContent(keyword);

        if(posts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // Endpoint to search comments by content keyword
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDocument>> searchComments(@RequestParam("keyword") String keyword) {
        List<CommentDocument> comments = searchService.searchCommentsByContent(keyword);

        if(comments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
