package com.realworld.study.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realworld.study.auth.handler.CustomLoginSuccessHandler;
import com.realworld.study.auth.jwt.JwtAuthenticationFilter;
import com.realworld.study.auth.jwt.JwtExceptionFilter;
import com.realworld.study.auth.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomLoginSuccessHandler customLoginSuccessHandler;
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement(
                config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin(config -> config
                .successHandler(customLoginSuccessHandler));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/posts", "/api/members").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
                .anyRequest().authenticated());

        http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(new JwtExceptionFilter(objectMapper),
                JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
