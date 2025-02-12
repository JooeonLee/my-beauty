package me.jooeon.mybeauty.global.common.exception.exception.member;

import me.jooeon.mybeauty.global.common.exception.exception.BusinessException;
import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;

public class MemberException extends BusinessException {
    public MemberException(BaseResponseStatus status) {
        super(status);
    }
}
