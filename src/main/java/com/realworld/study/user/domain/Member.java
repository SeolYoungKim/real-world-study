package com.realworld.study.user.domain;

import com.realworld.study.BaseTimeEntity;
import com.realworld.study.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {
    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column
    private String bio;

    @Column
    private String image;

    @OneToMany(mappedBy = "author")
    private final List<Post> posts = new ArrayList<>();

    public Member(final String email, final String password, final String name,
            final String bio, final String image) {
        this.email = new Email(email);
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.image = image;
    }

    public void addPost(final Post post) {
        posts.add(post);
    }
}
