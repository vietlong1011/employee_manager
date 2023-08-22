package com.o7planning.jwt;

import com.o7planning.service.user_security.UserDetailsCustom;
import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JwtService {

    // trich xuat va xac thuc token -> tra ve Claims
    Claims extractClaims(String token);

    // khoa de ky (chu ky so) va xac thuc
    Key getKey();

    // tao JWK tu thong tin ng dung cung cap
    String generateToken(UserDetailsCustom userDetailsCustom);

    // check tinh xac thuc
    boolean isValidToken(String token);
}
