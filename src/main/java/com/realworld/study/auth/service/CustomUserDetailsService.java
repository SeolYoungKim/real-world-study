package com.realworld.study.auth.service;

import com.realworld.study.member.domain.Email;
import com.realworld.study.member.domain.Member;
import com.realworld.study.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//TODO 회원 정보 조회 -> UserDetails 생성
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(new Email(username))
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 회원입니다."));

        return User.builder()
                .username(member.getEmailValue())
                .password(member.getPassword())
                .authorities("USER")
                .build();
    }
}
