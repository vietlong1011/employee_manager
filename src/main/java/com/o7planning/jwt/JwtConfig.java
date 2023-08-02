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
    @Value("${jwt.expiration:#{60*60}}")
    private int expiration;

    // khoa bi mat
    @Value("${jwt.secret:3979244226452948404D6251655468576D5A7134743777217A25432A462D4A61}")
    private String secret;
}
