package com.study.jwt.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.jwt.exception.ExceptionResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class LoginFailHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("LoginFailHandler-----------------------------------");

        ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.builder()
                .statusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message("아이디 또는 비밀번호가 틀렸습니다.")
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(HttpStatus.BAD_REQUEST.value());

        objectMapper.writeValue(response.getWriter(), exceptionResponseDto);
    }
}
