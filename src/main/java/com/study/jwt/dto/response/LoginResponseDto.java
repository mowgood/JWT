package com.study.jwt.dto.response;

import com.study.jwt.enumeration.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {

    private String accessToken;

    private String refreshToken;

    private String name;

    private RoleType roleType;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, String name, RoleType roleType) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.name = name;
        this.roleType = roleType;
    }
}