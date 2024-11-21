package me.jooeon.mybeauty.global;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
@Profile("test")
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors((cors) -> cors.disable()) // 모든 도메인 일시적 허용
                .csrf((auth) -> auth.disable()) // csrf 일시적 비활성화
                .formLogin((auth) -> auth.disable()) // 기본 로그인 폼 비활성화
                .httpBasic((auth) -> auth.disable()); // HTTP 헤더 인증 방식 비활성화

        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
