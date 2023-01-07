package com.realworld.study.article.domain;

import com.realworld.study.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Article {

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

    @Column(nullable = false, updatable = false, name="created_at")
    @CreatedDate
    protected LocalDateTime createdAt;

    @Column(nullable = false, name = "updated_at")
    @LastModifiedDate
    protected LocalDateTime updatedAt;

    public Article(final Long id,
            final String title,
            final String description,
            final String body,
            final User author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.body = body;
        this.author = author;
    }

    public Article(final String title,
            final String description,
            final String body,
            final User author) {
        this(null, title, description, body, author);
    }

    public void update(final String title,
            final String description,
            final String body) {
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
                ", author=" + author +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
