package com.realworld.study.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
import com.realworld.study.user.domain.User;
import com.realworld.study.user.domain.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ArticleQueryServiceTest {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleQueryService articleQueryService;

    @DisplayName("게시글 여러건 조회")
    @Nested
    class GetArticles {

        @DisplayName("2개를 모두 불러온다.")
        @Test
        void getArticles() {
            User author = new User("user@gmail.com", "user", "bio", null);
            userRepository.save(author);

            Article article1 = articleRepository.save(new Article("article", "description", "body", author));
            Article article2 = articleRepository.save(new Article("article2", "description2", "body2", author));

            List<Article> articles = articleQueryService.getArticles(Pageable.ofSize(2)).getContent();

            assertThat(articles.size()).isEqualTo(2);
            assertThat(articles.get(0)).isEqualTo(article1);
            assertThat(articles.get(1)).isEqualTo(article2);
        }
    }
}