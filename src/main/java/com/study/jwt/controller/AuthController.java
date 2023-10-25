package com.study.jwt.controller;

import com.study.jwt.dto.request.ReissueTokenRequestDto;
import com.study.jwt.dto.response.ReissueTokenResponseDto;
import com.study.jwt.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/refreshToken")
    public ResponseEntity<ReissueTokenResponseDto> reissueToken(@RequestBody ReissueTokenRequestDto request) {
        return ResponseEntity.ok(authService.reissueToken(request.getRefreshToken()));
    }
}