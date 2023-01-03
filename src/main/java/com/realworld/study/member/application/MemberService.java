package com.realworld.study.member.application;

import com.realworld.study.auth.util.UsernamePasswordAuthUtils;
import com.realworld.study.exception.domain.MemberNameDuplicationException;
import com.realworld.study.exception.domain.MemberNotFoundException;
import com.realworld.study.member.application.dto.MemberAuthResponse;
import com.realworld.study.member.application.dto.MemberProfileResponse;
import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.member.presentation.dto.MemberSignupRequest;
import com.realworld.study.member.presentation.dto.MemberUpdateRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberAuthResponse signup(final MemberSignupRequest memberSignupRequest) {
        Member member = getMemberBy(memberSignupRequest);
        memberRepository.save(member);

        //TODO token 발급 로직 필요
        String token = "token";

        return MemberAuthResponse.from(member, token);
    }

    private Member getMemberBy(final MemberSignupRequest dto) {
        String memberName = dto.getMemberName();
        validateDuplicationOf(memberName);

        return Member.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .memberName(memberName)
                .bio("")
                .image("")
                .build();
    }

    private void validateDuplicationOf(final String memberName) {
        if (memberRepository.existsByMemberName(memberName)) {
            throw new MemberNameDuplicationException();
        }
    }

    @Transactional(readOnly = true)
    public MemberAuthResponse currentMember(final Authentication authentication) {
        String email = UsernamePasswordAuthUtils.getEmail(authentication);
        Member member = memberRepository.findByEmail(new Email(email))
                .orElseThrow(MemberNotFoundException::new);

        String token = "token";  // 인증 객체로부터 획득

        return MemberAuthResponse.from(member, token);
    }

    public MemberAuthResponse updateMember(final MemberUpdateRequest updateRequest,
            final Authentication authentication) {
        String email = UsernamePasswordAuthUtils.getEmail(authentication);
        Member member = memberRepository.findByEmail(new Email(email))
                .orElseThrow(MemberNotFoundException::new);

        String token = "token";  // 인증 객체로부터 획득

        member.update(updateRequest.getEmail(), updateRequest.getBio(), updateRequest.getImage());
        return MemberAuthResponse.from(member, token);
    }

    @Transactional(readOnly = true)
    public MemberProfileResponse getProfile(String memberName) {
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(MemberNotFoundException::new);

        return MemberProfileResponse.from(member);
    }

    @PostConstruct
    public void init() {
        Member mem = Member.builder()
                .memberName("이름")
                .email("email@domain.com")
                .password(passwordEncoder.encode("123123123"))
                .build();

        memberRepository.save(mem);
    }
}
