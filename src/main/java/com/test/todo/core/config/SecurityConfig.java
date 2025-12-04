package com.test.todo.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.todo.api.controller.ExceptionHandlerController;
import com.test.todo.api.model.DefaultErrorResponseV1;
//import com.test.todo.core.security.JwtAuthenticationFilter;
import com.test.todo.core.security.JwtAuthenticationFilter;
import com.test.todo.core.service.AuthTokenService;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.OffsetDateTime;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(
            AuthTokenService authTokenService
    ) {
        return new JwtAuthenticationFilter(authTokenService);
    }

    @Bean
    SecureRandom secureRandom(){
        return new SecureRandom();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    Key key(@Value("${jwt.secret}") String secret) {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }

    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthTokenService authTokenService
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter(authTokenService), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
