package com.realworld.study.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.realworld.study.article.application.dto.ArticleCreateRequest;
import com.realworld.study.article.application.dto.ArticleUpdateRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
import com.realworld.study.user.domain.User;
import com.realworld.study.user.domain.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleServiceTest {

    static final String defaultUserEmail = "user@gmail.com";

    @Autowired
    ArticleService articleService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @BeforeEach
    void insertDefaultUser() {
        User user = new User(defaultUserEmail, "pw", "user", "bio", null);
        userRepository.save(user);
    }

    @DisplayName("게시글이 정상적으로 저장된다.")
    @Test
    void create() {
        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest("article", "this is an article", "body");
        Article saved = articleService.createArticle(defaultUserEmail, articleCreateRequest);

        Optional<Article> article = articleRepository.findById(saved.getId());

        assertThat(article.isPresent()).isEqualTo(true);
    }

    @DisplayName("게시글이 정상적으로 수정된다.")
    @Test
    void update() {
        final String articleTitle = "article1";
        Article article = createArticle(articleTitle);

        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest(
                "article2",
                "description2",
                "body2");
        articleService.updateArticle(defaultUserEmail, article.getId(), articleUpdateRequest);
        Article updated = articleRepository.findById(article.getId()).orElseThrow();

        assertThat(updated.getTitle()).isEqualTo("article2");
        assertThat(updated.getDescription()).isEqualTo("description2");
        assertThat(updated.getBody()).isEqualTo("body2");
    }

    @DisplayName("게시글이 정상적으로 삭제된다.")
    @Test
    void delete() {
        final String articleTitle = "article1";
        Article article = createArticle(articleTitle);

        articleService.deleteArticle(defaultUserEmail, article.getId());
        assertThat(articleRepository.existsById(article.getId())).isEqualTo(false);
    }

    @DisplayName("단건 조회가 정상적으로 작동된다.")
    @Test
    void getOne() {
        Article article1 = createArticle("article1");

        Article found1 = articleService.getArticle(article1.getId());

        assertThat(found1.getTitle()).isEqualTo("article1");
        assertThat(found1.getDescription()).isEqualTo("description");
        assertThat(found1.getBody()).isEqualTo("body");
    }

    Article createArticle(String title) {
        User author = getUser();

        return articleRepository.save(
                new Article(title, "description", "body", author));
    }

    User getUser() {
        return userRepository.findByEmail(defaultUserEmail).orElseThrow();
    }
}