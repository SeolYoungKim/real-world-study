package com.realworld.study.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.realworld.study.auth.FakeAuthentication;
import com.realworld.study.member.application.dto.MemberAuthResponse;
import com.realworld.study.member.application.dto.MemberProfileResponse;
import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.member.presentation.dto.MemberSignupRequest;
import com.realworld.study.member.presentation.dto.MemberUpdateRequest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private MemberService memberService;
    private Member member;

    private final String email = "email@domain.com";
    private final String password = "12345678";
    private final String name = "kim";

    //TODO 테스트 간 독립성을 위해 매번 새로 할당하는게 낫나? 아니면, 비용을 고려해 같은 객체를 사용하는게 낫나?
    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, passwordEncoder);
        member = Member.builder()
                .email(email)
                .password(password)
                .memberName(name)
                .build();
    }

    @DisplayName("회원 가입을 요청할 때")
    @Nested
    class Signup {
        @DisplayName("올바른 가입 정보가 넘어왔을 경우 MemberAuthResponse를 반환한다.")
        @Test
        void success() {
            when(memberRepository.save(any(Member.class))).thenReturn(member);
            when(passwordEncoder.encode(any(String.class))).thenReturn(password);

            MemberSignupRequest request = new MemberSignupRequest(email, password, name);
            MemberAuthResponse memberAuthResponse = memberService.signup(request);

            assertThat(memberAuthResponse.getEmail()).isEqualTo(email);
            assertThat(memberAuthResponse.getName()).isEqualTo(name);
        }

        @DisplayName("이미 존재하는 이름으로 가입을 요청할 경우 예외를 발생시킨다.")
        @Test
        void fail() {
            when(memberRepository.existsByMemberName(any(String.class))).thenReturn(true);
            MemberSignupRequest request = new MemberSignupRequest(email, password, name);

            assertThatThrownBy(() -> memberService.signup(request))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("이미 존재하는 이름입니다.");
        }
    }

    @DisplayName("현재 접속중인 회원의 정보를 조회할 때")
    @Nested
    class CurrentMember {
        @DisplayName("인증된 회원의 정보를 MemberAuthResponse로 반환한다.")
        @Test
        void success() {
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(member));

            Authentication fake = new FakeAuthentication();
            MemberAuthResponse memberAuthResponse = memberService.currentMember(fake);

            assertThat(memberAuthResponse.getEmail()).isEqualTo(email);
            assertThat(memberAuthResponse.getName()).isEqualTo(name);
        }

        @DisplayName("없는 회원일 경우 예외를 발생시킨다.")
        @Test
        void fail() {
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

            Authentication fake = new FakeAuthentication();
            assertThatThrownBy(() -> memberService.currentMember(fake))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("없는 회원입니다.");
        }
    }

    @DisplayName("회원의 정보를 업데이트할 때")
    @Nested
    class UpdateMember {
        private final String updatedEmail = "kim@domain.com";
        private final String updatedBio = "bio";
        private final String updatedImage = "image";

        @DisplayName("인증된 회원의 정보를 업데이트한 후, 업데이트된 정보를 MemberResponse로 반환한다.")
        @Test
        void success() {
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(member));

            MemberUpdateRequest request = new MemberUpdateRequest(updatedEmail, updatedBio, updatedImage);
            Authentication fake = new FakeAuthentication();
            MemberAuthResponse memberAuthResponse = memberService.updateMember(request, fake);

            assertThat(memberAuthResponse.getEmail()).isEqualTo(updatedEmail);
            assertThat(memberAuthResponse.getBio()).isEqualTo(updatedBio);
            assertThat(memberAuthResponse.getImage()).isEqualTo(updatedImage);
        }

        @DisplayName("없는 회원일 경우 예외를 발생시킨다.")
        @Test
        void fail() {
            when(memberRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

            MemberUpdateRequest request = new MemberUpdateRequest(updatedEmail, updatedBio, updatedImage);
            Authentication fake = new FakeAuthentication();

            assertThatThrownBy(() -> memberService.updateMember(request, fake))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("없는 회원입니다.");
        }
    }

    @DisplayName("특정 회원의 정보를 조회할 때")
    @Nested
    class GetProfile {
        @DisplayName("있는 회원일 경우 MemberProfileResponse를 반환한다.")
        @Test
        void success() {
            when(memberRepository.findByMemberName(any(String.class))).thenReturn(
                    Optional.of(member));

            MemberProfileResponse memberProfileResponse = memberService.getProfile(name);
            assertThat(memberProfileResponse.getName()).isEqualTo(name);
        }

        @DisplayName("없는 회원일 경우 예외를 발생시킨다.")
        @Test
        void fail() {
            when(memberRepository.findByMemberName(any(String.class))).thenReturn(
                    Optional.empty());

            assertThatThrownBy(() -> memberService.getProfile(name))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("없는 회원입니다.");
        }
    }
}