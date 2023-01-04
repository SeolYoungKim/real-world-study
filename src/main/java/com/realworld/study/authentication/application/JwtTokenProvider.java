package com.realworld.study.authentication.application;

public interface JwtTokenProvider {

    String createToken(String payload);

    boolean validateToken(String token);

    String getPayloadByKey(String token, String key);
}
