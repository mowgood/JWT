package com.study.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.jwt.auth.CustomAuthenticationProvider;
import com.study.jwt.auth.CustomUserDetailsService;
import com.study.jwt.auth.JwtProvider;
import com.study.jwt.auth.filter.JwtFilter;
import com.study.jwt.auth.filter.LoginFilter;
import com.study.jwt.auth.handler.Http401Handler;
import com.study.jwt.auth.handler.Http403Handler;
import com.study.jwt.auth.handler.LoginFailHandler;
import com.study.jwt.auth.handler.LoginSuccessHandler;
import com.study.jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // @Secured 활성화
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtProvider jwtProvider;

    private final MemberRepository memberRepository;

    private final ObjectMapper objectMapper;

//    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(customUserDetailsService, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("SecurityConfig-----------------------------------");

        return http
                .formLogin(form -> form
                        .disable())
                .httpBasic(basic -> basic
                        .disable())
                .csrf(AbstractHttpConfigurer::disable) // CSRF 토큰 비활성화
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/main").permitAll()
                        .requestMatchers("/auth/refreshToken").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(getLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(jwtProvider, objectMapper), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .accessDeniedHandler(new Http403Handler(objectMapper))
                        .authenticationEntryPoint(new Http401Handler(objectMapper)))
                .build();
    }

    private LoginFilter getLoginFilter() {
        LoginFilter loginFilter = new LoginFilter(new AntPathRequestMatcher("/auth/login", HttpMethod.POST.name()));
        loginFilter.setAuthenticationManager(authenticationManager());
        loginFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(jwtProvider, objectMapper));
        loginFilter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));

        return loginFilter;
    }
}
