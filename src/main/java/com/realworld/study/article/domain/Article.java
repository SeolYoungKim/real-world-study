package com.realworld.study.article.domain;

import com.realworld.study.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 50)
    private String description;

    @Column(nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    public Article(Long id, String title, String description, String body, User author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.body = body;
        this.author = author;
    }

    public Article(String title, String description, String body, User author) {
        this(null, title, description, body, author);
    }

    public void update(String title, String description, String body) {
        if (StringUtils.hasText(title)) {
            this.title = title;
        }
        if (StringUtils.hasText(description)) {
            this.description = description;
        }
        if (StringUtils.hasText(body)) {
            this.body = body;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }

        Article article = (Article) o;
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
