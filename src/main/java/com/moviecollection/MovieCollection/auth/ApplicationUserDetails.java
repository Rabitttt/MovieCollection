package com.moviecollection.MovieCollection.auth;


import com.moviecollection.MovieCollection.domain.User;

import com.moviecollection.MovieCollection.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@RequiredArgsConstructor
@Builder
@Getter
public class ApplicationUserDetails implements UserDetails {
    private final String id;
    private final String username;
    private final String password;
    private final Gender gender;
    private final String email;
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    public static ApplicationUserDetails fromUser(User user){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(String.valueOf(user.getRole())));
        return ApplicationUserDetails.builder()
                .id(String.valueOf(user.getId()))
                .username(user.getUserName())
                .password(user.getPassword())
                .gender(user.getGender())
                .email(user.getEmail())
                .grantedAuthorities(authorities)
                .build();
    }
}
