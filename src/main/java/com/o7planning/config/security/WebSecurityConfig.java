//package com.o7planning.config.security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebFluxSecurity
//public class WebSecurityConfig {
//
//    private  final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final AuthenticationProvider authenticationProvider;
//
//    public WebSecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }
//
//    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().ignoringRequestMatchers("/rest/***");
//
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.authorizeRequests()
//                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
//                .requestMatchers("api").authenticated()
//                .requestMatchers("/rest/login***").permitAll()
//                .requestMatchers("user").hasAuthority("ROLE_USER")
//                .and().csrf().disable().authorizeRequests().anyRequest().authenticated()
//                .and().authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//
//    }
//}
