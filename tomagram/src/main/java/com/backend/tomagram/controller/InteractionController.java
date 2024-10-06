package com.backend.tomagram.controller;

import com.backend.tomagram.dto.InteractionRequest;
import com.backend.tomagram.models.Comment;
import com.backend.tomagram.models.Like;
import com.backend.tomagram.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/interactions")
public class InteractionController {
    private final InteractionService interactionService;

    @PostMapping("/comments")
    public Comment addComment(@RequestHeader("Authorization") String authHeader,@RequestBody InteractionRequest req) {
        return interactionService.addComment(authHeader, req);
    }

    @PostMapping("/likes")
    public Like addLike(@RequestHeader("Authorization") String authHeader,@RequestBody InteractionRequest req) {
        return interactionService.addLike(authHeader, req);
    }

    @DeleteMapping("/likes")
    public void removeLike(@RequestHeader("Authorization") String authHeader,@RequestBody InteractionRequest req) {
        interactionService.removeLike(authHeader, req);
    }

    @GetMapping("/comments/{postId}")
    public List<Comment> getCommentsForPost(@PathVariable long postId) {
        return interactionService.getCommentsForPost(postId);
    }

    @GetMapping("/likes/{postId}")
    public List<Like> getLikesForPost(@PathVariable long postId) {
        return interactionService.getLikesForPost(postId);
    }
}
