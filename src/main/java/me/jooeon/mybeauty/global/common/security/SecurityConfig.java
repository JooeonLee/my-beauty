package me.jooeon.mybeauty.global.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.jooeon.mybeauty.domain.auth.filter.JwtFilter;
import me.jooeon.mybeauty.domain.auth.application.CustomOAuth2UserService;
import me.jooeon.mybeauty.domain.auth.utils.JwtUtil;
import me.jooeon.mybeauty.domain.auth.presentation.handler.CustomAuthenticationSuccessHandler;
import me.jooeon.mybeauty.domain.member.model.Role;
import me.jooeon.mybeauty.global.common.exception.handler.FilterExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final JwtUtil jwtUtil;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtUtil);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors((cors) -> cors.disable()) // 모든 도메인 일시적 허용
                .csrf((auth) -> auth.disable()) // csrf 일시적 비활성화
                .formLogin((auth) -> auth.disable()) // 기본 로그인 폼 비활성화
                .httpBasic((auth) -> auth.disable()); // HTTP 헤더 인증 방식 비활성화

        http
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new FilterExceptionHandler(new ObjectMapper()), JwtFilter.class);

        http
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // 인증 실패 처리
                        .accessDeniedHandler(customAccessDeniedHandler) // 권한 부족 처리
                );

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/api-json/**", "/api-docs", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                        .requestMatchers("/oauth2/**","/login/**", "/reissue", "/api/login/success", "/docs/**", "/app/api/image/**").permitAll()
                        //.anyRequest().authenticated() // 이상함
                        .anyRequest().hasRole(Role.MEMBER.getValue())
                );


        // oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)))
                        .successHandler(customAuthenticationSuccessHandler));

        return http.build();
    }
}
