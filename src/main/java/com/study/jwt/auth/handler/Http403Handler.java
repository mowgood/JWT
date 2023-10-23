package com.study.jwt.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.jwt.exception.ExceptionResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
public class Http403Handler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("Http403Handler-----------------------------------");

        ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.builder()
                .statusCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
                .message("요청에 접근할 수 없습니다.")
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(HttpStatus.FORBIDDEN.value());

        objectMapper.writeValue(response.getWriter(), exceptionResponseDto);
    }
}
