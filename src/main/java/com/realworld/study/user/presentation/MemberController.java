package com.realworld.study.user.presentation;

import com.realworld.study.user.application.MemberService;
import com.realworld.study.user.application.dto.MemberAuthResponse;
import com.realworld.study.user.presentation.dto.MemberSignupRequest;
import com.realworld.study.user.presentation.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
