package com.backend.tomagram.controller;

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
    public Comment addComment(@PathVariable long postId, @RequestParam String userId, @RequestBody String content) {
        return interactionService.addComment(postId, userId, content);
    }

    @PostMapping("/likes")
    public Like addLike(@PathVariable long postId, @RequestParam String userId) {
        return interactionService.addLike(postId, userId);
    }

    @DeleteMapping("/likes")
    public void removeLike(@PathVariable long postId, @RequestParam String userId) {
        interactionService.removeLike(postId, userId);
    }

    @GetMapping("/comments")
    public List<Comment> getCommentsForPost(@PathVariable long postId) {
        return interactionService.getCommentsForPost(postId);
    }

    @GetMapping("/likes")
    public List<Like> getLikesForPost(@PathVariable long postId) {
        return interactionService.getLikesForPost(postId);
    }
}
