package com.realworld.study.comment.presentation;

import com.realworld.study.comment.application.CommentService;
import com.realworld.study.comment.application.dto.CommentDeleteResponse;
import com.realworld.study.comment.application.dto.CommentResponse;
import com.realworld.study.comment.presentation.dto.CommentCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponse createComment(
            @PathVariable final Long postId,
            @RequestBody final CommentCreateRequest commentCreateRequest,
            final Authentication authentication
    ) {
        return commentService.createComment(postId, commentCreateRequest, authentication);
    }

    @GetMapping("/comments")
    public Page<CommentResponse> getComments(@PathVariable final Long postId,
            final Pageable pageable) {
        return commentService.getComments(postId, pageable);
    }

    @DeleteMapping("/comments/{commentsId}")
    public CommentDeleteResponse deleteComment(
            @PathVariable final Long postId,
            @PathVariable final Long commentsId,
            Authentication authentication
    ) {
        return commentService.deleteComment(postId, commentsId, authentication);
    }
}
