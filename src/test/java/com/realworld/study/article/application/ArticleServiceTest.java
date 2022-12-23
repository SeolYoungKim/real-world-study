package com.realworld.study.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.realworld.study.article.application.dto.ArticleCreateRequest;
import com.realworld.study.article.application.dto.ArticleUpdateRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
import com.realworld.study.article.domain.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleRepository articleRepository;

    @DisplayName("게시글이 정상적으로 저장된다.")
    @Test
    void create() {
        User author = new User("user@gmail.com", "user", "bio", null);
        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest("article", "this is an article", "body", author);
        Article saved = articleService.createArticle(articleCreateRequest);

        Optional<Article> article = articleRepository.findById(saved.getId());

        assertThat(article.isPresent()).isEqualTo(true);
    }

    @DisplayName("게시글이 정상적으로 수정된다.")
    @Test
    void update() {
        User author = new User("user@gmail.com", "user", "bio", null);
        Article article = articleRepository.save(new Article("article", "description", "body", author));

        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest("article2", "description2", "body2");
        articleService.updateArticle(article.getId(), articleUpdateRequest);
        
        Article updated = articleRepository.findById(article.getId()).orElseThrow();

        assertThat(updated.getTitle()).isEqualTo("article2");
        assertThat(updated.getDescription()).isEqualTo("description2");
        assertThat(updated.getBody()).isEqualTo("body2");
    }

    @DisplayName("게시글이 정상적으로 삭제된다.")
    @Test
    void delete() {
        User author = new User("user@gmail.com", "user", "bio", null);
        Article article = articleRepository.save(new Article("article", "description", "body", author));
        articleService.deleteArticle(article.getId());

        assertThat(articleRepository.existsById(article.getId())).isEqualTo(false);
    }

    @DisplayName("단건 조회가 정상적으로 작동된다.")
    @Test
    void getOne() {
        User author = new User("user@gmail.com", "user", "bio", null);
        Article article1 = articleRepository.save(new Article("article", "description", "body", author));
        Article article2 = articleRepository.save(new Article("article2", "description2", "body2", author));

        Article found1 = articleService.getArticle(article1.getId());

        assertThat(found1.getTitle()).isEqualTo("article");
        assertThat(found1.getDescription()).isEqualTo("description");
        assertThat(found1.getBody()).isEqualTo("body");
    }
}