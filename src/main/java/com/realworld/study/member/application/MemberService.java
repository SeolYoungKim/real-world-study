package com.realworld.study.member.application;

import com.realworld.study.member.application.dto.MemberAuthResponse;
import com.realworld.study.member.application.dto.MemberProfileResponse;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import com.realworld.study.member.presentation.dto.MemberSignupRequest;
import com.realworld.study.member.presentation.dto.MemberUpdateRequest;
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
        String memberName = dto.getMemberName();
        validateDuplicationOf(memberName);

        return new Member(dto.getEmail(), dto.getPassword(), memberName,
                "", "");
    }

    private void validateDuplicationOf(final String memberName) {
        if (memberRepository.existsByMemberName(memberName)) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다.");
        }
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

    @Transactional(readOnly = true)
    public MemberProfileResponse getProfile(String memberName) {
        Member member = memberRepository.findByMemberName(memberName)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        return MemberProfileResponse.from(member);
    }
}
