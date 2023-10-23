package com.study.jwt.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.jwt.auth.JwtProvider;
import com.study.jwt.domain.Member;
import com.study.jwt.dto.response.LoginResponseDto;
import com.study.jwt.exception.MemberNotFoundException;
import com.study.jwt.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("LoginSuccessHandler-----------------------------------");

        Member member = memberRepository.findByMemberId(authentication.getName()).orElseThrow(MemberNotFoundException::new);

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(HttpStatus.OK.value());

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .accessToken(jwtProvider.generateAccessToken(member.getMemberId(), "access"))
                .refreshToken(jwtProvider.generateRefreshToken(member.getMemberId(), "refresh"))
                .name(member.getName())
                .roleType(member.getRoleType())
                .build();

        new ObjectMapper().writeValue(response.getWriter(), loginResponseDto);
    }
}
