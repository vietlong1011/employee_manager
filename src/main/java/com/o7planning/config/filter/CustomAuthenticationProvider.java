package com.o7planning.config.filter;


import com.o7planning.entity.user.Role;
import com.o7planning.entity.user.User;
import com.o7planning.exception.BaseException;
import com.o7planning.repository.URepository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// quan trong trong xac thuc

/**
 * xác thực thông tin đăng nhập của người dùng dựa trên dữ liệu được lưu trữ trong cơ sở dữ liệu
 **/
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {
        log.info("Start actual authentication");

        // lay username va password cua ng dung
        final String username = authentication.getName();

        final String password = authentication.getCredentials().toString();

        User user;
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "User's not found");
        }

        // lay danh sach cac Role cua user dua theo username duoc truyen xuong Db
        final List<GrantedAuthority> authorityList = getAuthorities(user.getRoles().stream().toList());

        // tra ve Obj authen
        final Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorityList);

        log.info("End actual authentication");
        return auth;
    }

    // method nay nhan danh sach cac roles cua user va chuyen chung thanh danh sach cac GrantedAuthority
    //GrantedAuthority : danh sach dai dien cho mot so quyen cu the
    private List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> result = new ArrayList<>();
        Set<String> permissions = new HashSet<>();

        // neu danh sach cac roles k rong thi duyet qua tung roles trong danh sach , them vao set
        if (!ObjectUtils.isEmpty(roles)) {
            roles.forEach(r -> {
                permissions.add(r.getName());
            });
        }

        // duyet qua tung quyen trong Set , moi quyen theo 1 obj SimpleGrantedAuthority vao result
        permissions.forEach(p -> {
            result.add(new SimpleGrantedAuthority(p));
        });
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
