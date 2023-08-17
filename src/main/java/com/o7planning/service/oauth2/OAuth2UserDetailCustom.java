package com.o7planning.service.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

// thiet lap cac cau hinh Oauth2 de chua thong tin cua user
/**
 * Lớp OAuth2UserDetailCustom là một lớp được tạo ra để triển khai các giao diện UserDetails và OAuth2User từ Spring Security.
 * Mục đích chính của lớp này là để lưu trữ thông tin của người dùng được xác thực thông qua OAuth2.**/
public class OAuth2UserDetailCustom implements OAuth2User , UserDetails {

    private Long id;

    private  String username;

    private String password;

    private List<GrantedAuthority> authorities;

    private Map<String,Object> attributes;

    private boolean isEnabled;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;


    public OAuth2UserDetailCustom(Long id , String username,String password,List<GrantedAuthority> authorities){
        this.id = id;
        this.password = password;
        this.username = username;
        this.authorities = authorities;
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
        return isCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
