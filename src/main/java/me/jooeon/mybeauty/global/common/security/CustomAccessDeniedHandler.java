package me.jooeon.mybeauty.global.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        BaseResponseStatus status = BaseResponseStatus.ACCESS_DENIED;
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(status.getHttpStatus().value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(Map.of(
                "isSuccess", status.isSuccess(),
                "responseCode", status.getResponseCode(),
                "responseMessage", status.getResponseMessage()
        )));
    }
}
