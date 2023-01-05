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
}
