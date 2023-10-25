package com.study.jwt.controller;

import com.study.jwt.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/api/main")
    @Operation(summary = "메인 API", description = "메인 API 입니다.")
    public String main() {
        return "main API";
    }

    @GetMapping("/api/member")
    @Secured("ROLE_USER")
    @Operation(summary = "멤버 API", description = "멤버 API 입니다.")
    public String member(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userDetails.getName() + " " + userDetails.getRoleType();
    }
}
