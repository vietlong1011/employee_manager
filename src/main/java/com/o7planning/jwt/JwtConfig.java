package com.o7planning.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

// Class này chứa các giá trị cấu hình (configuration values) cho việc tạo và xác thực JSON Web Token (JWT) trong ứng dụng.
@Data
public class JwtConfig {

    //đường dẫn của endpoint để tạo JWT
    @Value("${jwt.url:/jwt/login}")
    private String url;

    // ten cua header
    @Value("${jwt.header:Authorization}")
    private String header;

    // tien to cua header
    @Value("${jwt.prefix:Bearer}")
    private String prefix;

    // time live
    @Value("${jwt.expiration:#{3600000}}") //one hour
    private long expiration;

    // khoa bi mat
     @Value("${jwt.secret:3979244226452948404D6251655468576D5A7134743777217A25432A462D4A61}")
//    @Value("${jwt.secret: 404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String secret;

    @Value("${jwt.refresh_exp:#{604800000}}") // two hour
    private long refreshExpiration;
}
