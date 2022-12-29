package com.realworld.study.auth;

import com.realworld.study.auth.service.CustomAuthenticationProvider;
import com.realworld.study.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.formLogin();

        http.authenticationProvider(
                new CustomAuthenticationProvider(userDetailsService, passwordEncoder()));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/posts").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/api/members")
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
