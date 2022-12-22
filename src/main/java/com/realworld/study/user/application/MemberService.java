package com.realworld.study.user.application;

import com.realworld.study.user.application.dto.MemberAuthResponse;
import com.realworld.study.user.domain.Member;
import com.realworld.study.user.domain.MemberRepository;
import com.realworld.study.user.presentation.dto.MemberSignupRequest;
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
}
