package com.realworld.study.article.domain;

import com.realworld.study.common.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String email;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 100)
    private String bio;

    @Column(nullable = false, length = 100)
    private String image;

    public User(Long id, String email, String username, String bio, String image) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public User(String email, String username, String bio, String image) {
        this(null, email, username, bio, image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", bio='" + bio + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
