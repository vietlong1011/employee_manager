package com.o7planning.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.o7planning.dto.LoginRequest;
import com.o7planning.entity.Provider;
import com.o7planning.entity.user.Role;
import com.o7planning.entity.user.Tokens;
import com.o7planning.entity.user.TokenType;
import com.o7planning.entity.user.User;
import com.o7planning.entity.user.dao.DataDAO;
import com.o7planning.jwt.jwt.JwtServiceImpl;
import com.o7planning.repository.URepository.TokenRepository;
import com.o7planning.repository.URepository.RoleRepository;
import com.o7planning.repository.URepository.UserRepository;

import com.o7planning.service.UserService;
import com.o7planning.service.user_security.UserDetailsCustom;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    @Lazy
    private JwtServiceImpl jwtServiceImpl;

    private final TokenRepository tokenRepository;

    private final DataDAO dao;


    public AuthenticationResponse register(LoginRequest request) {
        var user = User.builder() // add new account and generate token
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles( request.getRole())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .providerID(Provider.local.name())
                .build();

        // yeu cau login la tu dong gen duoc token roi
        // dang thiet lap api de gen ra access token va refresh token jwtConfig.accessToken , jwtConfig. refresh token
        User u = userRepository.findUserByUsername(request.getUsername());// check xem user ton tai chua
        Optional<User> optionalUser = Optional.ofNullable(u);
        if (!optionalUser.isEmpty()) { // neu ton tai -> xoa user, token trong DB thay bang cai moi
            tokenRepository.deleteTokenByUser_Id(optionalUser.get().getId());
            userRepository.deleteUserById(optionalUser.get().getId());
        }


        var saveUser = userRepository.save(user);
        UserDetailsCustom userDetailsCustom = new UserDetailsCustom();
        userDetailsCustom.setUsername(request.getUsername());
        userDetailsCustom.setPassword(request.getPassword());
        userDetailsCustom.setAuthorityList(convertRolesToAuthorities( request.getRole()));

        var jwtToken = jwtServiceImpl.generateToken(userDetailsCustom);
        System.out.println( "\nToken: "+ jwtToken);
        var refreshToken = jwtServiceImpl.generateRefreshToken(userDetailsCustom);
        saveUserToken(saveUser, refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Tokens.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(true)
                .revoked(true)
                .build();
        tokenRepository.save(token);
    }


    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId()); // check xem co ban gi cua revoked co gia tri tru k?
        if (validUserTokens.isEmpty()) return; // neu k co -> ket thuc , nguoc lai revoke -> false
        ;
        validUserTokens.forEach(tokens -> {
            tokens.setExpired(false);
            tokens.setRevoked(false);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        if (Boolean.FALSE.equals(tokenRepository.findRevokedByToken(refreshToken))) { // check xem token con han su dung hay da bi thay the
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            System.err.println("Token expired\n");
            return;
        }

        try {
            username = jwtServiceImpl.extractUsername(refreshToken);// trich xuat username tu refreshToken
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            System.err.println("Token expired\n" + e);
            return;
        }

        if (username != null) {
            var user = this.userRepository.findUserByUsername(username);
            if (jwtServiceImpl.isValidToken(refreshToken)) {

                UserDetailsCustom userDetailsCustom = new UserDetailsCustom();
                userDetailsCustom.setUsername(user.getUsername());
                userDetailsCustom.setPassword(user.getPassword());
                userDetailsCustom.setAuthorityList(roleConvertAuthority(username));

                var accessToken = jwtServiceImpl.generateToken(userDetailsCustom); //create new accessToken
                revokeAllUserTokens(user);//thu hoi All token
                saveUserToken(user, accessToken); // save new token and new refresh token to DB
                var authResponse = AuthenticationResponse.builder() //return accessToken and refreshToken to view
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse); //convert authResponse (JSON) save output stream response return user
            }
        }
    }

    public List<GrantedAuthority> roleConvertAuthority(String username) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> listRole = dao.findRolesByUsername(username);
        for (String role : listRole) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            authorities.add(authority);
        }
        return authorities;
    }

    public  List<GrantedAuthority> convertRolesToAuthorities(Set<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
            authorities.add(authority);
        }

        return authorities;
    }

}
