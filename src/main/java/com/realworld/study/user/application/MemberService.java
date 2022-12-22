package com.realworld.study.user.application;

import com.realworld.study.user.application.dto.MemberAuthResponse;
import com.realworld.study.user.domain.Member;
import com.realworld.study.user.domain.MemberRepository;
import com.realworld.study.user.presentation.dto.MemberSignupRequest;
import com.realworld.study.user.presentation.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberAuthResponse signup(final MemberSignupRequest memberSignupRequest) {
        Member member = getMemberBy(memberSignupRequest);
        memberRepository.save(member);

        //TODO token 발급 로직 필요
        String token = "token";

        return MemberAuthResponse.from(member, token);
    }

    private Member getMemberBy(final MemberSignupRequest dto) {
        return new Member(dto.getEmail(), dto.getPassword(), dto.getMemberName(),
                "", "");
    }

    @Transactional(readOnly = true)
    public MemberAuthResponse currentMember() {
        //TODO 인증 객체가 넘어오면 그를 기반으로 멤버를 조회해야 한다.
        Long memberId = 1L;  // 인증 객체로부터 획득
        String token = "token";  // 인증 객체로부터 획득
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        return MemberAuthResponse.from(member, token);
    }

    public MemberAuthResponse updateMember(MemberUpdateRequest updateRequest) {
        //TODO 인증 객체가 넘어오면 그를 기반으로 멤버를 조회해야 한다.
        Long memberId = 1L;  // 인증 객체로부터 획득
        String token = "token";  // 인증 객체로부터 획득
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
        member.update(updateRequest.getEmail(), updateRequest.getBio(), updateRequest.getImage());

        return MemberAuthResponse.from(member, token);
    }
}
