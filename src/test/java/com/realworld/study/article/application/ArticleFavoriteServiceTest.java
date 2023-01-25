package com.realworld.study.article.application;

import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleFavorite;
import com.realworld.study.article.domain.ArticleFavoriteRepository;
import com.realworld.study.article.domain.ArticleRepository;
import com.realworld.study.user.domain.User;
import com.realworld.study.user.domain.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleFavoriteServiceTest {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleFavoriteRepository articleFavoriteRepository;
    @Autowired
    ArticleFavoriteService articleFavoriteService;

    User defaultUser;
    Article defaultArticle;

    @BeforeEach
    void insertDefaultEntities() {
        defaultUser = new User("user@gmail.com", "pw", "user", "bio", null);
        userRepository.save(defaultUser);

        defaultArticle = new Article("article", "description", "body", defaultUser);
        articleRepository.save(defaultArticle);
    }

    @Nested
    class IsArticleFavorite {

        @Test
        void favorite() {
            createArticleFavorite();

            boolean articleFavorite = articleFavoriteService.isArticleFavorite(
                    defaultUser.getEmail(),
                    defaultArticle.getId());
            Assertions.assertThat(articleFavorite).isTrue();
        }

        @Test
        void notFavorite() {
            boolean articleFavorite = articleFavoriteService.isArticleFavorite(
                    defaultUser.getEmail(),
                    defaultArticle.getId());
            Assertions.assertThat(articleFavorite).isFalse();
        }
    }

    @Test
    void favoriteArticle() {
        articleFavoriteService.favoriteArticle(defaultUser.getEmail(), defaultArticle.getId());

        boolean articleFavorite = articleFavoriteService.isArticleFavorite(
                defaultUser.getEmail(),
                defaultArticle.getId());
        Assertions.assertThat(articleFavorite).isTrue();
    }

    @Test
    void unfavoriteArticle() {
        createArticleFavorite();
        articleFavoriteService.unfavoriteArticle(defaultUser.getEmail(), defaultArticle.getId());

        boolean articleFavorite = articleFavoriteService.isArticleFavorite(
                defaultUser.getEmail(),
                defaultArticle.getId());
        Assertions.assertThat(articleFavorite).isFalse();
    }

    void createArticleFavorite() {
        ArticleFavorite favorite = new ArticleFavorite(defaultArticle, defaultUser);
        articleFavoriteRepository.save(favorite);
    }
}