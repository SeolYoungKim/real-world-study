package com.realworld.study.member.domain;

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
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Column(nullable = false)
    private String password;

    @Column
    private String memberName;

    @Column
    private String bio;

    @Column
    private String image;

    @OneToMany(mappedBy = "author")
    private final List<Post> posts = new ArrayList<>();

    public Member(final String email, final String password, final String memberName,
            final String bio, final String image) {
        validatePassword(password);
        validateMemberName(memberName);
        this.email = new Email(email);
        this.password = password;
        this.memberName = memberName;
        this.bio = bio;
        this.image = image;
    }

    public void addPost(final Post post) {
        posts.add(post);
    }

    public String getEmailValue() {
        return email.getValue();
    }

    public void update(final String email, final String bio, final String image) {
        this.email = new Email(email);
        this.bio = bio;  //TODO 없앨 가능성도 고려해서 따로 null 체크 안함
        this.image = image;
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 8자리 이상 입력해 주세요.");
        }
    }

    private void validateMemberName(String memberName) {
        if (!StringUtils.hasText(memberName)) {
            throw new IllegalArgumentException("이름은 필수 항목입니다.");
        }
    }
}
