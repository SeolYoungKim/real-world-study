package com.realworld.study.authentication;

import com.realworld.study.authentication.application.JwtTokenProvider;

/**
 *  JWT Token API
 */
public class JwtTokenProviderImpl implements JwtTokenProvider {

    @Override
    public String createToken(String payload) {
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public String getPayloadByKey(String token, String key) {
        return null;
    }
}
