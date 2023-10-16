package com.study.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/api/main")
    @Operation(summary = "메인 API", description = "메인 API 입니다.")
    public String main() {
        return "main API";
    }
}
