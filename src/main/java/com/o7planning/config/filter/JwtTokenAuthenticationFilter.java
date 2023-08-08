package com.o7planning.config.filter;

import com.o7planning.jwt.JwtConfig;
import com.o7planning.jwt.JwtService;
import com.o7planning.utils.BaseResponseDTO;
import com.o7planning.utils.HelperUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // lay ten cua header de giai ma
        String accessToken = request.getHeader(jwtConfig.getHeader());

        log.info("Start do filter once per request, {}", request.getRequestURI());
        // kiem tra xem token rong hay khong va co theo regex (Prefix + " " ) hay k , hop le loai bo Prefix va space
        if (!ObjectUtils.isEmpty(accessToken) && accessToken.startsWith(jwtConfig.getPrefix())) {
            accessToken = accessToken.substring((jwtConfig.getPrefix() + " ").length());

            try {
                // xac thuc tinh hop le -> tra ve obj claims vs role
                if (jwtService.isValidToken(accessToken)) {
                    Claims claims = jwtService.extractClaims(accessToken);

                    String username = claims.getSubject();

                    List<String> authorities = claims.get("authorities", List.class);
                    // neu username khong rong thi se lay no va cap quyen cho no roi gui len FE
                    if (!ObjectUtils.isEmpty(username)) {
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        username, null,
                                        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                        SecurityContextHolder.getContext().setAuthentication(auth);

                    }
                }
            } catch (Exception e) {
                log.error("Error on filter once per request, path {}, error: {}", request.getRequestURI(), e.getMessage());
                BaseResponseDTO responseDTO = new BaseResponseDTO();
                responseDTO.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
                responseDTO.setMessage(e.getLocalizedMessage());

                String json = HelperUtils.JSON_WRITER.writeValueAsString(responseDTO);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json; charset=UTF-8");
                response.getWriter().write(json);
                return;
            }
        }
        log.info("end do filter: {}", request.getRequestURI());
        filterChain.doFilter(request, response);

    }


}

