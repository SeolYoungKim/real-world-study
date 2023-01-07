package com.realworld.study.article.application;

import com.realworld.study.article.application.dto.ArticleCreateRequest;
import com.realworld.study.article.application.dto.ArticleUpdateRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
import com.realworld.study.article.exception.InvalidArticleException;
import com.realworld.study.common.exception.ErrorType;
import com.realworld.study.common.exception.ForbiddenException;
import com.realworld.study.user.domain.User;
import com.realworld.study.user.domain.UserRepository;
import com.realworld.study.user.exception.InvalidUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public Article createArticle(
            final String authorEmail,
            final ArticleCreateRequest articleCreateRequest) {

        User author = getAuthorByEmail(authorEmail);
        Article article = new Article(articleCreateRequest.getTitle(),
                articleCreateRequest.getDescription(),
                articleCreateRequest.getBody(),
                author);

        return articleRepository.save(article);
    }

    public Article getArticle(final Long articleId) {
        return getArticleById(articleId);
    }

    public Article updateArticle(final String authorEmail,
            final Long articleId,
            final ArticleUpdateRequest articleUpdateRequest) {
        User author = getAuthorByEmail(authorEmail);
        Article article = getArticleById(articleId);
        validateAuthor(author, article);

        article.update(articleUpdateRequest.getTitle(),
                articleUpdateRequest.getDescription(),
                articleUpdateRequest.getBody());

        return articleRepository.save(article);
    }

    public void deleteArticle(final String authorEmail, final Long articleId) {
        User author = getAuthorByEmail(authorEmail);
        Article article = getArticleById(articleId);
        validateAuthor(author, article);

        articleRepository.delete(article);
    }

    private void validateAuthor(final User author, final Article article) {
        if (!article.getAuthor().equals(author)) {
            throw new ForbiddenException(ErrorType.FORBIDDEN);
        }
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
