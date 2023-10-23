package com.study.jwt.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final CustomUserDetailsService customUserDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    @Value("${jwt.access-token-validity-in-seconds}")
    private long accessTokenValidTime;

    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidTime;

    @PostConstruct
    protected void init() {
        String encodeKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(encodeKey.getBytes());
    }

    public String generateToken(String authId, String subject, Long tokenValidTime) {

        log.info("JwtProvider-----------------------------------");

        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenValidTime);

        return Jwts.builder()
                .setSubject(subject)
                .claim("auth", authId)
                .setIssuedAt(now) // 발행 시간
                .setExpiration(expiration) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 비밀키, 해싱 알고리즘
                .compact();
    }

    public String generateAccessToken(String authId, String subject) {
        return generateToken(authId, subject, accessTokenValidTime);
    }

    public String generateRefreshToken(String authId, String subject) {
        return generateToken(authId, subject, refreshTokenValidTime);
    }
}