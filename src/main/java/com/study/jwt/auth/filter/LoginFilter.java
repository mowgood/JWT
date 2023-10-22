package com.study.jwt.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.jwt.dto.request.LoginRequestDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * 인증 단계 처리
 */
@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("LoginFilter-----------------------------------");

        if(request.getMethod().equalsIgnoreCase("GET")) {
            throw new HttpRequestMethodNotSupportedException("잘못된 요청입니다.");
        }

        LoginRequestDto loginRequestDto = new ObjectMapper().readValue(request.getReader(), LoginRequestDto.class);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getMemberId(), loginRequestDto.getPassword());

        return getAuthenticationManager().authenticate(authenticationToken);
    }
}
