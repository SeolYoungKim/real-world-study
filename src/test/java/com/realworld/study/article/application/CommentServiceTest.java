package com.realworld.study.article.application;

import com.realworld.study.article.application.dto.CommentAddRequest;
import com.realworld.study.article.domain.Article;
import com.realworld.study.article.domain.ArticleRepository;
import com.realworld.study.article.domain.Comment;
import com.realworld.study.article.domain.CommentRepository;
import com.realworld.study.user.domain.User;
import com.realworld.study.user.domain.UserRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentServiceTest {

    static final String defaultUserEmail = "user@gmail.com";
    static final String defaultArticleTitle = "article";

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentService commentService;

    User defaultUser;
    Article defaultArticle;

    @BeforeEach
    void insertDefaultEntities() {
        defaultUser = new User(defaultUserEmail, "pw", "user", "bio", null);
        userRepository.save(defaultUser);

        defaultArticle = new Article(defaultArticleTitle, "description", "body", defaultUser);
        articleRepository.save(defaultArticle);
    }

    @Test
    void addCommentToArticle() {
        CommentAddRequest commentAddRequest = new CommentAddRequest("body");
        commentService.addCommentToArticle(defaultUserEmail, defaultArticle.getId(), commentAddRequest);

        List<Comment> comments = commentRepository.findByArticle_Id(defaultArticle.getId());
        Comment comment = comments.get(0);
        Assertions.assertThat(comment.getBody()).isEqualTo("body");
        Assertions.assertThat(comment.getArticle()).isEqualTo(defaultArticle);
        Assertions.assertThat(comment.getAuthor()).isEqualTo(defaultUser);
    }

    @Test
    void getCommentsFromArticle() {
        Comment comment1 = createComment("body1");
        Comment comment2 = createComment("body2");

        List<Comment> founds = commentService.getCommentsFromArticle(
                defaultArticle.getId());

        Assertions.assertThat(founds).contains(comment1, comment2);
    }

    @Test
    void deleteComment() {
        Comment comment1 = createComment("body1");

        commentService.deleteComment(defaultUserEmail, comment1.getId());

        boolean exists = commentRepository.existsById(comment1.getId());
        Assertions.assertThat(exists).isFalse();
    }

    Comment createComment(String body) {
        return commentRepository.save(new Comment(body, defaultArticle, defaultUser));
    }
}