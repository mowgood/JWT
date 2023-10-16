package com.study.jwt.auth;

import com.study.jwt.domain.Member;
import com.study.jwt.exception.MemberNotFoundException;
import com.study.jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 유저의 정보를 가져온다
 */

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(username).orElseThrow(MemberNotFoundException::new);

        return CustomUserDetails.builder()
                .id(member.getId())
                .name(member.getName())
                .memberId(member.getMemberId())
                .password(member.getPassword())
                .roleType(member.getRoleType())
                .build();
    }
}
