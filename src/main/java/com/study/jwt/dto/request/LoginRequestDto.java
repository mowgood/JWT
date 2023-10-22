package com.study.jwt.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {

    @NotBlank(message = "아이디를 입력하세요.")
    private String memberId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}
