package com.study.jwt.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.jwt.auth.JwtProvider;
import com.study.jwt.exception.ExceptionResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        log.info("TokenCheckFilter(Path)-----------------------------------");
//
//        String path = request.getRequestURI();
//        if (!(path.startsWith("/auth/") || path.startsWith("/api/"))) {
//            log.info("유효한 경로가 아닙니다.");
//            filterChain.doFilter(request, response);
//            return;
//        }

        log.info("JwtFilter(AccessToken)-----------------------------------");

        String authorization = request.getHeader("Authorization");

        if (authorization == null) {
            log.info("accessToken이 존재하지 않습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String tokenType = authorization.substring(0, 6);
        String accessToken = authorization.substring(7);

        if (!tokenType.equalsIgnoreCase("Bearer")) {
            log.info("유효하지 않은 토큰입니다.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtProvider.validateToken(accessToken)) {
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            getExceptionResponse(response, HttpStatus.FORBIDDEN, "인증이 만료되었습니다.");
        } catch (Exception e) {
            getExceptionResponse(response, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
        }
    }

    private void getExceptionResponse(HttpServletResponse response, HttpStatus status, String errorMessage) {
        log.info("getExceptionResponse-----------------------------------");
        ExceptionResponseDto exceptionResponseDto = ExceptionResponseDto.builder()
                .statusCode(String.valueOf(status.value()))
                .message(errorMessage)
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF_8.name());
        response.setStatus(status.value());

        try {
            objectMapper.writeValue(response.getWriter(), exceptionResponseDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
