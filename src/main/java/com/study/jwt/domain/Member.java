package com.study.jwt.domain;

import com.study.jwt.enumeration.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String memberId;

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Builder
    public Member(String name, String memberId, String password, RoleType roleType) {
        this.name = name;
        this.memberId = memberId;
        this.password = password;
        this.roleType = roleType;
    }
}
