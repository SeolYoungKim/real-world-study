package com.realworld.study.article.presentation;

import com.realworld.study.article.application.ArticleFavoriteService;
import com.realworld.study.article.application.ArticleQueryService;
import com.realworld.study.article.application.ArticleService;
import com.realworld.study.article.application.CommentService;
import com.realworld.study.article.application.dto.ArticleCreateRequest;
import com.realworld.study.article.application.dto.ArticleUpdateRequest;
import com.realworld.study.article.application.dto.CommentAddRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.Comment;
import com.realworld.study.article.presentation.dto.ArticleResponse;
import com.realworld.study.article.presentation.dto.CommentResponse;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleQueryService articleQueryService;
    private final CommentService commentService;

    private final ArticleFavoriteService articleFavoriteService;
    @PostMapping
    public ArticleResponse createArticle(final Principal principal,
            @RequestBody final ArticleCreateRequest articleCreateRequest) {
        String userEmail = principal.getName();

        Article article = articleService.createArticle(userEmail, articleCreateRequest);
        return ArticleResponse.of(article);
    }

    @GetMapping
    public Page<ArticleResponse> listArticles(final Pageable pageable) {
        return articleQueryService
                .getArticles(pageable)
                .map(ArticleResponse::of);
    }

    @GetMapping("/{id}")
    public ArticleResponse getArticle(final Principal principal,
            @PathVariable final Long id) {
        boolean favorited = isFavorited(principal, id);
        return ArticleResponse.of(articleService.getArticle(id), favorited);
    }

    @PutMapping("/{id}")
    public ArticleResponse updateArticle(final Principal principal,
            @PathVariable final Long id,
            @RequestBody final ArticleUpdateRequest articleUpdateRequest) {
        String userEmail = principal.getName();
        Article updated = articleService.updateArticle(userEmail, id, articleUpdateRequest);

        boolean favorited = isFavorited(principal, id);
        return ArticleResponse.of(updated, favorited);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(final Principal principal,
            @PathVariable final Long id) {
        String userEmail = principal.getName();
        articleService.deleteArticle(userEmail, id);
    }

    @PostMapping("/{id}/comments")
    public CommentResponse addCommentToArticle(final Principal principal,
            @PathVariable final Long id,
            @RequestBody final CommentAddRequest commentAddRequest) {
        String userEmail = principal.getName();
        Comment comment = commentService.addCommentToArticle(userEmail, id, commentAddRequest);
        return CommentResponse.of(comment);
    }

    @GetMapping("/{id}/comments")
    public List<CommentResponse> getCommentsFromArticle(@PathVariable final Long id) {
        List<Comment> comments = commentService.getCommentsFromArticle(id);
        return comments.stream()
                .map(CommentResponse::of)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public void deleteComment(final Principal principal,
            @PathVariable final Long id,
            @PathVariable final Long commentId) {
        String userEmail = principal.getName();
        commentService.deleteComment(userEmail, commentId);
    }

    @PostMapping("/{id}/favorite")
    public void favoriteArticle(final Principal principal,
            @PathVariable final Long id) {
        String userEmail = principal.getName();
        articleFavoriteService.favoriteArticle(userEmail, id);
    }

    @DeleteMapping("/{id}/favorite")
    public void unfavoriteArticle(final Principal principal,
            @PathVariable final Long id) {
        String userEmail = principal.getName();
        articleFavoriteService.unfavoriteArticle(userEmail, id);
    }

    private boolean isFavorited(final Principal principal, final Long id) {
        boolean favorited = false;
        if (principal != null) {
            String userEmail = principal.getName();
            favorited = articleFavoriteService.isArticleFavorite(userEmail, id);
        }
        return favorited;
    }
}
