package me.jooeon.mybeauty.domain.auth.presentation;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.domain.auth.model.dto.JwtInfo;
import me.jooeon.mybeauty.domain.auth.utils.CookieUtil;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final CookieUtil cookieUtil;

    @GetMapping("/success")
    public BaseResponse<JwtInfo> success(HttpServletRequest request) {

        // 쿠키에서 토큰 받기
        String accessToken = cookieUtil.getCookieValue(request, "ACCESS_TOKEN");
        String refreshToken = cookieUtil.getCookieValue(request, "REFRESH_TOKEN");

        // JwtInfo 객체 생성
        JwtInfo jwtInfo = new JwtInfo(accessToken, refreshToken);


        return new BaseResponse<>(BaseResponseStatus.SUCCESS, jwtInfo);
    }

}
