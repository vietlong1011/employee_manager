package com.o7planning.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o7planning.dto.LoginRequest;
import com.o7planning.jwt.JwtConfig;
import com.o7planning.jwt.JwtService;
import com.o7planning.service.user_security.UserDetailsCustom;
import com.o7planning.utils.BaseResponseDTO;
import com.o7planning.utils.HelperUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Collections;

/** class nay dung de xac thuc JWT**/
@Slf4j
public class JwtUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtService jwtService;

    private final ObjectMapper objectMapper;

    //cấu hình filter xác thực yêu cầu đăng nhập dựa trên tên người dùng và mật khẩu
    // yeu cau JWT -> login -> xac thuc -> tao ra 1 obj khop vs yeu cau
    public JwtUsernamePasswordAuthenticationFilter(AuthenticationManager manager,
                                                   JwtConfig jwtConfig,
                                                   JwtService jwtService) {
        //tạo ra một đối tượng AntPathRequestMatcher để so khớp yêu cầu HTTP chỉ với đường dẫn được chỉ định trong jwtConfig.getUrl() và phương thức HTTP là POST.
        super(new AntPathRequestMatcher(jwtConfig.getUrl(), "POST"));
        setAuthenticationManager(manager); // xac thuc yeu cau login
        this.objectMapper = new ObjectMapper();
        this.jwtService = jwtService;
    }

    // ham sinh ra access token , xac thuc
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        log.info("Start attempt to authentication");
        LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        log.info("End attempt to authentication");

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword(),
                        Collections.emptyList()
                ));
    }

    // sau khi xac thuc thanh cong se vao day xu ly xac thuc thanh cong va thuc hien cac hanh dong sau khi xac thuc

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserDetailsCustom userDetailsCustom = (UserDetailsCustom) authResult.getPrincipal(); // authen success return Obj
        String accessToken = jwtService.generateToken(userDetailsCustom); // chuyen doi token theo obj
        String json = HelperUtils.JSON_WRITER.writeValueAsString(accessToken); // chuyen doi chuoi token sang Json
        response.setContentType("application/json; charset=UTF-8"); // cau hinh noi dung chi dinh reponse 1 obj Json
        response.getWriter().write(json); // chuoi Json duoc ghi vao de gui phan hoi cho ng dung
        log.info("End success authentication: {}", accessToken);

    }

    // tra ve neu xac thuc khong thanh cong
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        // tao Obj phan hoi UNAUTHORIZED khi xac thuc k thanh cong
        BaseResponseDTO responseDTO = new BaseResponseDTO();
        responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        responseDTO.setMessage(failed.getLocalizedMessage());

        String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
        return;
    }

}
