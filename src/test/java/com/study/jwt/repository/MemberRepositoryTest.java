package com.study.jwt.repository;

import com.study.jwt.domain.Member;
import com.study.jwt.enumeration.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 데이터 30개 추가")
    public void createMember() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                    .name("멤버" + i)
                    .memberId("member" + i)
                    .password(passwordEncoder.encode("0000"))
                    .roleType(RoleType.ROLE_USER)
                    .build();

            memberRepository.save(member);
        });
    }
}