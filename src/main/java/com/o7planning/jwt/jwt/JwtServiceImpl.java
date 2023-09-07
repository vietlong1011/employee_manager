package com.o7planning.jwt.jwt;

import com.o7planning.jwt.JwtConfig;
import com.o7planning.jwt.JwtService;
import com.o7planning.exception.BaseException;
import com.o7planning.service.user_security.UserDetailsCustom;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * class nay de ma hoa JWT (tao JWT va trich xuat data Override)
 * **/
@Service
@RequiredArgsConstructor
@Slf4j
@Data
public class JwtServiceImpl implements JwtService {


  private final JwtConfig jwtConfig;

  private final UserDetailsService userDetailsService;



    //phương thức này tạo ra một JWT từ thông tin người dùng được cung cấp.
    @Override
    public String generateToken(UserDetailsCustom userDetailsCustom) {


        List<String> roles = new ArrayList<>();

        userDetailsCustom.getAuthorities().forEach( role -> {
            roles.add(role.getAuthority());
        });

        log.info("Roles: {} ", roles);

        return Jwts.builder()
                .setSubject(userDetailsCustom.getUsername())
                .claim("authorities", (userDetailsCustom.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())))
                .claim("roles", roles)
                .claim("isEnable",userDetailsCustom.isEnabled())
//                .setIssuedAt(Date.from(now)) //Định nghĩa thời gian phát hành của JWT,
//                .setExpiration(Date.from(now.plusSeconds(jwtConfig.getExpiration())))//Định nghĩa thời gian hết hạn của JWT (3600 ke tu thoi diem bat dau)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(getKey(), SignatureAlgorithm.HS256) // tao chu ky so cho JWT dua theo ma key va thuat toan SHA256
                .compact();
    }

// phương thức này trích xuất các thông tin từ JWT được mã hóa và trả về các biến Claims (các thuộc tính trong JWT).
    @Override
    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //phương thức này trả về khóa được sử dụng để ký và xác thực JWT.
    @Override
    public Key getKey() {
        byte[] key = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(key);
    }



    @Override
    public boolean isValidToken(String token) {
      final  String username = extractUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return !ObjectUtils.isEmpty(userDetails);
    }


    /**3 method duoi ho tro cho viec check tinh hop le cua token trong viec giai ma**/

    // trich xuat usernam tu token
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject); // method reference -> sub (id) xac dinh chu the cua id
    }

    //phương thức này trích xuất các thông tin từ JWT bằng cách sử dụng một hàm chức năng được cung cấp.
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    //phương thức này trích xuất tất cả các Claims từ JWT.
    private Claims extractAllClaims(String token){
        Claims claims; // Claims bao gom sub , iat , exp

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token expiration");
        }catch (UnsupportedJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token's not supported");
        }catch (MalformedJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Invalid format 3 part of token");
        }catch (SignatureException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Invalid format token");
        }catch (Exception e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), e.getLocalizedMessage());
        }

        return claims;
    }

    // refresh token
    @Override
    public String generateRefreshToken(UserDetailsCustom userDetailsCustom) {
        return buildToken(new HashMap<>(),userDetailsCustom,jwtConfig.getRefreshExpiration());
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetailsCustom userDetailsCustom ,
            long expiration
    ){

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetailsCustom.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(),SignatureAlgorithm.HS256)
                .compact();
    }

}
