package com.study.jwt.controller;

import com.study.jwt.auth.JwtProvider;
import com.study.jwt.dto.response.ReissueTokenResponseDto;
import com.study.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtProvider jwtProvider;

    @PostMapping("/auth/refreshToken")
    public ResponseEntity<Void> reissueToken(@CookieValue String refreshToken) {
        ReissueTokenResponseDto responseDto = authService.reissueToken(refreshToken);
        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", responseDto.getRefreshToken())
                .maxAge(jwtProvider.refreshTokenValidTime)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .header(HttpHeaders.AUTHORIZATION, jwtProvider.jwtType + responseDto.getAccessToken())
                .build();
    }
}