package me.jooeon.mybeauty.global.common.exception.exception;

import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;

public class JwtException extends BusinessException {
    public JwtException(BaseResponseStatus status) {
        super(status);
    }
}
