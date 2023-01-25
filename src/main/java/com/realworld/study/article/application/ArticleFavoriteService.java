package com.realworld.study.article.application;

import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleFavorite;
import com.realworld.study.article.domain.ArticleFavoriteRepository;
import com.realworld.study.article.domain.ArticleRepository;
import com.realworld.study.article.exception.InvalidArticleException;
import com.realworld.study.article.exception.InvalidFavoriteArticleException;
import com.realworld.study.common.exception.ErrorType;
import com.realworld.study.user.domain.User;
import com.realworld.study.user.domain.UserRepository;
import com.realworld.study.user.exception.InvalidUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleFavoriteService {

    private final ArticleFavoriteRepository articleFavoriteRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public boolean isArticleFavorite(final String userEmail,
            final Long articleId) {
        User user = getUserByEmail(userEmail);
        Article article = getArticleById(articleId);
        return existsArticleFavoriteByArticleAndUser(user, article);
    }

    public void favoriteArticle(final String userEmail,
            final Long articleId) {
        User user = getUserByEmail(userEmail);
        Article article = getArticleById(articleId);

        boolean existsArticleFavorite = existsArticleFavoriteByArticleAndUser(user, article);
        if (existsArticleFavorite) {
            throw new InvalidFavoriteArticleException(ErrorType.ALREADY_FAVORITED_ARTICLE);
        }

        articleFavoriteRepository.save(new ArticleFavorite(article, user));
    }

    public void unfavoriteArticle(final String userEmail,
            final Long articleId) {
        User user = getUserByEmail(userEmail);
        Article article = getArticleById(articleId);

        ArticleFavorite articleFavorite = getArticleFavorite(user, article);

        articleFavoriteRepository.delete(articleFavorite);
    }

    private boolean existsArticleFavoriteByArticleAndUser(final User user,
            final Article article) {
        return articleFavoriteRepository.existsByArticle_IdAndUser_Id(article.getId(), user.getId());
    }

    private ArticleFavorite getArticleFavorite(final User user,
            final Article article) {
        return articleFavoriteRepository.findArticleFavoriteByArticle_IdAndUser_Id(article.getId(), user.getId())
                .orElseThrow(() -> new InvalidFavoriteArticleException(ErrorType.NOT_FAVORITED_ARTICLE));
    }

    private User getUserByEmail(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidUserException(ErrorType.NOT_FOUND_USER));
    }

    private Article getArticleById(final Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new InvalidArticleException(ErrorType.NOT_FOUND_ARTICLE));
    }
}
