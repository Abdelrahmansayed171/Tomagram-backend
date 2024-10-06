package com.backend.tomagram.service;

import com.backend.tomagram.models.Comment;
import com.backend.tomagram.repository.CommentRepository;
import com.backend.tomagram.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InteractionService {
    private final CommentRepository commentRepo;
    private final LikeRepository likeRepo;

}
