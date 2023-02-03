package com.realworld.study.article.application;

import com.realworld.study.article.application.dto.CommentAddRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
import com.realworld.study.article.domain.Comment;
import com.realworld.study.article.domain.CommentRepository;
import com.realworld.study.article.exception.InvalidArticleException;
import com.realworld.study.article.exception.InvalidCommentException;
import com.realworld.study.common.exception.ErrorType;
import com.realworld.study.common.exception.ForbiddenException;
import com.realworld.study.user.domain.User;
import com.realworld.study.user.domain.UserRepository;
import com.realworld.study.user.exception.InvalidUserException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public Comment addCommentToArticle(final String authorEmail,
            final Long articleId,
            final CommentAddRequest commentAddRequest) {
        User author = getAuthorByEmail(authorEmail);
        Article article = getArticleById(articleId);

        Comment comment = new Comment(commentAddRequest.getBody(), article, author);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsFromArticle(final Long articleId) {
        return commentRepository.findByArticle_Id(articleId);
    }

    public void deleteComment(final String authorEmail,
            final Long commentId) {
        User author = getAuthorByEmail(authorEmail);
        Comment comment = getCommentById(commentId);

        validateAuthor(author, comment);

        commentRepository.delete(comment);
    }

    private void validateAuthor(final User author, final Comment comment) {
        if (!comment.getAuthor().equals(author)) {
            throw new ForbiddenException(ErrorType.FORBIDDEN);
        }
    }

    private Comment getCommentById(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new InvalidCommentException(ErrorType.NOT_FOUND_COMMENT));
    }

    private Article getArticleById(final Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new InvalidArticleException(ErrorType.NOT_FOUND_ARTICLE));
    }

    private User getAuthorByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));
    }
}
