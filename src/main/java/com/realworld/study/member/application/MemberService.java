package com.realworld.study.member.application;

import com.realworld.study.member.application.dto.MemberAuthResponse;
import com.realworld.study.member.application.dto.MemberProfileResponse;
import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.member.presentation.dto.MemberSignupRequest;
import com.realworld.study.member.presentation.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
    }

    @Transactional(readOnly = true)
    public MemberAuthResponse currentMember(final Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        Member member = memberRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        String token = "token";  // 인증 객체로부터 획득

        return MemberAuthResponse.from(member, token);
    }

    public MemberAuthResponse updateMember(final MemberUpdateRequest updateRequest,
            final Authentication authentication) {
        String email = authentication.getPrincipal().toString();
        Member member = memberRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        String token = "token";  // 인증 객체로부터 획득

        member.update(updateRequest.getEmail(), updateRequest.getBio(), updateRequest.getImage());
        return MemberAuthResponse.from(member, token);
    }

    @Transactional(readOnly = true)
    public MemberProfileResponse getProfile(String memberName) {
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        return MemberProfileResponse.from(member);
    }
}
