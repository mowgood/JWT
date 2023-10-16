package com.study.jwt.auth;

import com.study.jwt.enumeration.RoleType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 유저 정보
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private Long id;

    private String name;

    private String memberId;

    private String password;

    private RoleType roleType;

    private Collection<GrantedAuthority> authorities;

    @Builder
    public CustomUserDetails(Long id, String name, String memberId, String password, RoleType roleType) {
        this.id = id;
        this.name = name;
        this.memberId = memberId;
        this.password = password;
        this.roleType = roleType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
