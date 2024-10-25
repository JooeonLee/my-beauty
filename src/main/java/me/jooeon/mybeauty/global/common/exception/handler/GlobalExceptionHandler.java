package me.jooeon.mybeauty.global.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponse;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static me.jooeon.mybeauty.global.common.model.dto.BaseResponseStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * MethodArgumentTypeMismatchException 예외를 처리하는 핸들러입니다.
     *
     * 이 메서드는 메서드 인자 타입이 일치하지 않을 때 발생하는 예외를 처리합니다.
     * HttpStatus.METHOD_NOT_ALLOWED(405) 상태 코드와 함계 에러 메시지를 반환합니다.
     *
     * @param e MethodArgumentTypeMismatchException 예외 객체, 예외가 발생한 필드 이름과 메시지를 포함합니다.
     * @return BaseResponse 객체로 에러코드와 예외 정보를 포함한 JSON 객체를 반환합니다.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public BaseResponse<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        JSONObject result = new JSONObject();
        result.put(e.getName(), e.getMessage());
        return new BaseResponse<>(METHOD_ARGUMENT_TYPE_MISMATCH, result);
    }
}
