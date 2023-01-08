package com.realworld.study.auth.application;

import com.realworld.study.auth.application.dto.SignUpRequestDto;
import com.realworld.study.user.domain.User;
import com.realworld.study.user.domain.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void defaultUser() {
        User user = new User("email",
                passwordEncoder.encode("1234"),
                "test",
                "bio",
                null);

        userRepository.save(user);
    }

    public void signUpUser(final SignUpRequestDto signUpRequestDto) {
        User user = new User(signUpRequestDto.getEmail(),
                passwordEncoder.encode(signUpRequestDto.getPassword()),
                signUpRequestDto.getUsername(),
                "", null);

        userRepository.save(user);
    }
}
