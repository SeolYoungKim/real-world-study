package com.realworld.study.member.presentation;

import com.realworld.study.member.application.MemberService;
import com.realworld.study.member.application.dto.MemberAuthResponse;
import com.realworld.study.member.application.dto.MemberProfileResponse;
import com.realworld.study.member.presentation.dto.MemberSignupRequest;
import com.realworld.study.member.presentation.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    public MemberAuthResponse signup(@RequestBody MemberSignupRequest memberSignupRequest) {
        return memberService.signup(memberSignupRequest);
    }

    @GetMapping("/member")
    public MemberAuthResponse currentMember() {
        return memberService.currentMember();
    }

    @PutMapping("/member")
    public MemberAuthResponse updateMember(@RequestBody MemberUpdateRequest updateRequest) {
        return memberService.updateMember(updateRequest);
    }

    @GetMapping("/profiles/{memberName}")
    public MemberProfileResponse getProfile(@PathVariable String memberName) {
        return memberService.getProfile(memberName);
    }
}
