package com.realworld.study.auth.jwt;

import com.realworld.study.auth.Role;
import com.realworld.study.auth.util.UsernamePasswordAuthUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtProvider {
    private static final String AUTHORITIES_KEY = "authority";
    private static final String BEARER = "Bearer ";
    private static final String BLANK = "";

    private final long expireTime;
    private final Key key;

    public JwtProvider(
            @Value("${jwt.token.secret-key}") String secretKey,
            @Value("${jwt.token.expire-length}") long expireTime
    ) {
        this.expireTime = expireTime;

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String accessToken(Authentication authentication) {
        String email = UsernamePasswordAuthUtils.getEmail(authentication);
        Date expireTime = getExpireTime();

        return Jwts.builder()
                .setSubject(email)
                .claim(AUTHORITIES_KEY, Role.USER.key())
                .setExpiration(expireTime)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    private Date getExpireTime() {
        long now = new Date().getTime();
        return new Date(now + expireTime);
    }

    public String resolveTokenFrom(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length());
        }

        return BLANK;
    }

    public boolean validateJwt(String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtNotAvailable("토큰이 만료되었습니다. 다시 로그인 해주세요.");
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException e) {
            throw new JwtNotAvailable("올바르지 않은 토큰입니다.");
        }
    }

    public Authentication authentication(String jwt) {
        Claims claims = claims(jwt);
        String email = claims.getSubject();

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(
                claims.get(AUTHORITIES_KEY).toString());

        User user = new User(email, BLANK, authorities);
        return new UsernamePasswordAuthenticationToken(user, BLANK, authorities);
    }

    private Claims claims(String jwt) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtNotAvailable("토큰이 만료되었습니다. 다시 로그인 해주세요.");
        }
    }
}
