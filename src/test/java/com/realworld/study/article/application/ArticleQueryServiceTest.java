package com.realworld.study.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
import com.realworld.study.user.domain.User;
import com.realworld.study.user.domain.UserRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleQueryServiceTest {

    static final String defaultUserEmail = "user@gmail.com";

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleQueryService articleQueryService;

    @BeforeEach
    void insertDefaultUser() {
        User user = new User(defaultUserEmail, "pw", "user", "bio", null);
        userRepository.save(user);
    }

    @DisplayName("게시글 여러건 조회")
    @Nested
    class GetArticles {

        @DisplayName("2개를 모두 불러온다.")
        @Test
        void getArticles() {
            Article article1 = createArticle("article1");
            Article article2 = createArticle("article2");

            List<Article> articles = articleQueryService.getArticles(Pageable.ofSize(2)).getContent();

            assertThat(articles.size()).isEqualTo(2);
            assertThat(articles.get(0)).isEqualTo(article2);
            assertThat(articles.get(1)).isEqualTo(article1);
        }
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