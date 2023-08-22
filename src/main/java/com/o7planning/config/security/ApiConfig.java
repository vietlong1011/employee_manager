package com.o7planning.config.security;

import com.o7planning.config.filter.CustomAuthenticationProvider;
import com.o7planning.config.filter.JwtTokenAuthenticationFilter;
import com.o7planning.config.filter.JwtUsernamePasswordAuthenticationFilter;
import com.o7planning.exception.CustomAccessDeniedHandler;
import com.o7planning.jwt.JwtConfig;
import com.o7planning.jwt.JwtService;
import com.o7planning.service.oauth2.CustomOAuth2UserDetailService;
import com.o7planning.service.oauth2.hander.CustomOAuth2FailureHandler;
import com.o7planning.service.oauth2.hander.CustomOAuth2SuccessHandler;
import com.o7planning.service.user_security.UserDetailsServiceCustom;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity// annotation bao ve cho phep su dung phan quyen truoc va sau
public class ApiConfig {

    @Autowired
    private CustomOAuth2UserDetailService customOAuth2UserDetailService;

    @Autowired
    private CustomOAuth2FailureHandler customOAuth2FailureHandler;

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceCustom();
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

        AuthenticationManager manager = builder.build();

        http
                .cors().disable()
                // xu ly xac thuc co ban
                .authorizeHttpRequests()
                .requestMatchers("/account/**").permitAll()
                .requestMatchers("/guest/**").authenticated()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/user").hasAuthority("USER")
                .anyRequest().authenticated() // moi request phai login
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/sign-in")// duong dan den trang login
                .defaultSuccessUrl("/home/index", true) //mac dinh , neu login thanh cong
                .permitAll()
                .and()
                // xu ly khi thuc hien logout
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID") // xoa cookies hien tai
                .clearAuthentication(true) // loai bo quyen
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                // thiet lap khi gap cac trang k cho phep
                .exceptionHandling()
                .accessDeniedPage("/403")
                .and()
                .csrf().disable()
                .authenticationManager(manager)
                .httpBasic()
                .and()

                // cau hinh JWT
                //cấu hình quản lý phiên, xác thực người dùng và xử lý ngoại lệ trong Spring Security
                .authenticationManager(manager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        ((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                )
                // cấu hình xử lý truy cập bị từ chối và thêm các bộ lọc để xác thực và xác thực JWT
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtUsernamePasswordAuthenticationFilter(manager, jwtConfig, jwtService), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig, jwtService), UsernamePasswordAuthenticationFilter.class)

                // cai dat oauth2
                .oauth2Login() // kich hoat oauth2
                .loginPage("/login") // return page login
                .defaultSuccessUrl("/home/index", true) // login success return page home
                .userInfoEndpoint()
                .userService(customOAuth2UserDetailService) // lay thong tin tu ung dung cua nguoi dung uy quyen (username , email,role,..)
                .and()
                // dinh nghia cac thanh sau khi qua trinh xac thua OAuth2 thanh cong hoac that bai
                .successHandler(customOAuth2SuccessHandler) // trong TH nay tra ve page home
                .failureHandler(customOAuth2FailureHandler) // trong TH nay tra ve login
                .and()// tao 1 session khi login success
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

        ;

        return http.build();
    }

    // khong yeu cau xac thuc cac tep nay
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) ->
                web.ignoring()
                        .requestMatchers("/js/**", "/css/**");
    }

}

//cau hinh nhieu tk cua OAuth2