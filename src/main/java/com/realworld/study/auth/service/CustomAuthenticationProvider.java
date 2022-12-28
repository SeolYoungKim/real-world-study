package com.realworld.study.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        // 넘어온 authentication은 필터가 만들어준거임. 즉, 우리가 로그인창에 입력한 정보임.
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // userDetailsService를 통해 아이디로 사용자를 조회. 이는 DB에 저장되어있던 회원 정보임.
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        validatePassword(userDetails, password);

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), password,
                userDetails.getAuthorities());
    }

    private void validatePassword(UserDetails userDetails, String password) {
        String savedPassword = userDetails.getPassword();

        if (!passwordEncoder.matches(password, savedPassword)) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
