package com.realworld.study.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.realworld.study.article.application.dto.ArticleRequest;
import com.realworld.study.article.application.dto.ArticleUpdateRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
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
    void save() {
        ArticleRequest articleRequest = new ArticleRequest("article", "this is an article", "body");
        Article saved = articleService.save(articleRequest);

        Optional<Article> article = articleRepository.findById(saved.getId());

        assertThat(article.isPresent()).isEqualTo(true);
    }

    @DisplayName("게시글이 정상적으로 수정된다.")
    @Test
    void update() {
        Article article = articleRepository.save(new Article("article", "description", "body"));

        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest("article2", "description2", "body2");
        articleService.update(article.getId(), articleUpdateRequest);
        
        Article updated = articleRepository.findById(article.getId()).get();

        assertThat(updated.getTitle()).isEqualTo("article2");
        assertThat(updated.getDescription()).isEqualTo("description2");
        assertThat(updated.getBody()).isEqualTo("body2");
    }

    @DisplayName("게시글이 정상적으로 삭제된다.")
    @Test
    void delete() {
        Article article = articleRepository.save(new Article("article", "description", "body"));
        articleService.delete(article.getId());

        assertThat(articleRepository.existsById(article.getId())).isEqualTo(false);
    }
}