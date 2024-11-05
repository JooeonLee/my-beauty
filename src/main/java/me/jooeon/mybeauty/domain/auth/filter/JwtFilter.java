package me.jooeon.mybeauty.domain.auth.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.utils.JwtUtil;
import me.jooeon.mybeauty.domain.auth.model.AuthDto;
import me.jooeon.mybeauty.domain.auth.model.dto.CustomOAuth2User;
import me.jooeon.mybeauty.domain.member.model.Role;
import me.jooeon.mybeauty.global.common.exception.exception.JwtException;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponseStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public final static List<String> PASS_URIS = Arrays.asList(
            "/login",
            "/login",
            "/api/login/success"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("Request URI: {}", request.getRequestURI()); // 요청 URI 로깅

        if(isPassUris(request.getRequestURI())) {
            log.info("JWT Filter Passed (pass uri) : {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = resolveToken(request);

        if(accessToken == null) {
            log.info("JWT Filter Pass (accessToken is null) : {}", request.getRequestURI());
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰 유효성 검사
        try {
            jwtUtil.isTokenExpired(accessToken);
        } catch (ExpiredJwtException e) {
            throw new JwtException(BaseResponseStatus.EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            throw new JwtException(BaseResponseStatus.INVALID_ACCESS_TOKEN);
        }

        // 토큰 타입 검사
        if(!"access".equals(jwtUtil.getTokenType(accessToken))) {
            throw new JwtException(BaseResponseStatus.INVALID_TOKEN_TYPE);
        }

        // 권한 리스트 생성
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(jwtUtil.getRole(accessToken)));
        log.info("Granted Authorities : {}", authorities);

        //todo
        // CustomOAuth2User 생성
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(AuthDto.builder()
                .memberId(jwtUtil.getMemberId(accessToken))
                .providerId(jwtUtil.getProviderId(accessToken))
                .role(Role.fromRole(jwtUtil.getRole(accessToken)))
                //.build(), authorities);
                .build());

        log.info("CustomOAuth2User created: {}", customOAuth2User); // 생성된 사용자 정보 로깅
        log.info("CustomOAuth2User.providerId: {}", customOAuth2User.getProviderId());
        log.info("CustomOAuth2User.role: {}", customOAuth2User.getAuthorities().stream().findFirst().get().toString());
        log.info("CustomOAuth2User.memberId: {}", customOAuth2User.getMemberId());

        //todo
        // 스프링 시큐리티 인증 토큰 생성(Authentication)
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
        log.info("Authentication set in SecurityContext: {}", SecurityContextHolder.getContext().getAuthentication()); // SecurityContext 설정 확인 로깅
        log.info("Authorities in SecurityContext: {}", authToken.getAuthorities());

        log.info("JWT Filter Success : {}", request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        log.info("token = {}", bearerToken);
        if((StringUtils.hasText(bearerToken)) && bearerToken.startsWith(jwtUtil.BEARER)) {
            log.info("token = {}", bearerToken);
            return bearerToken.substring(jwtUtil.BEARER.length());
        }
        return null;
    }

    private boolean isPassUris(String uri) {
        return PASS_URIS.contains(uri);
    }
}
