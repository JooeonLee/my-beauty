package me.jooeon.mybeauty.global.common.exception.exception;

import lombok.Getter;
import me.jooeon.mybeauty.global.common.model.dto.BaseResponseStatus;

@Getter
public class BusinessException extends RuntimeException {
    private final BaseResponseStatus baseResponseStatus;

    public BusinessException(BaseResponseStatus baseResponseStatus) {
        super(baseResponseStatus.getResponseMessage());
        this.baseResponseStatus = baseResponseStatus;
    }
}
