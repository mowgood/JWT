package com.study.jwt.service;

import com.study.jwt.auth.JwtProvider;
import com.study.jwt.dto.response.ReissueTokenResponseDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtProvider jwtProvider;

    public ReissueTokenResponseDto reissueToken(String refreshToken) {
        try {
            if (jwtProvider.validateToken(refreshToken)) {
                Claims claims = jwtProvider.parseClaims(refreshToken);
                String userId = claims.get("auth").toString();
                String newRefreshToken = jwtProvider.generateAccessToken(userId, "refresh");

                return ReissueTokenResponseDto.builder()
                        .accessToken(jwtProvider.generateAccessToken(userId, newRefreshToken))
                        .refreshToken(newRefreshToken)
                        .build();
            }
        } catch (NullPointerException e) {
            throw new NullPointerException("요청에 실패하였습니다.");
        }
        return null;
    }
}
