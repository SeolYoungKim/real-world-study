package com.realworld.study.auth.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class UsernamePasswordAuthUtils {
    private UsernamePasswordAuthUtils() {
    }

    public static String getEmail(final Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return user.getUsername();
    }
}
