package com.realworld.study.comment.presentation;

import com.realworld.study.comment.application.CommentResponse;
import com.realworld.study.comment.application.CommentService;
import com.realworld.study.comment.presentation.dto.CommentCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public CommentResponse createComment(
            @PathVariable final Long postId,
            @RequestBody final CommentCreateRequest commentCreateRequest,
            final Authentication authentication
    ) {
        return commentService.createComment(postId, commentCreateRequest, authentication);
    }

//    @GetMapping("/posts/{postId}/comments")
//    public CommentResponse readComment(@PathVariable Long postId) {
//
//    }
}