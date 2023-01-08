package com.realworld.study.user.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {



    @GetMapping("/login/success")
    public String loginSuccess() {
//        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();

//        return ResponseEntity.ok(UserResponse.of(userDetails));
        return "hi";
    }

    // 인증 객체가 필요할땐 Authorization을 의존성 주입받을 수 있다!
}
