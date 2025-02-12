package me.jooeon.mybeauty.global.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BaseResponseStatus status = BaseResponseStatus.UNAUTHORIZED_ACCESS;

        response.setContentType("application/json;charset=utf-8");
        response.setStatus(status.getHttpStatus().value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(Map.of(
                "isSuccess", status.isSuccess(),
                "responseCode", status.getResponseCode(),
                "responseMessage", status.getResponseMessage()
        )));
    }
}
