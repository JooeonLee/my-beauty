package me.jooeon.mybeauty.global.common.exception.exception.article;

import me.jooeon.mybeauty.global.common.exception.exception.BusinessException;
import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;

public class CommentException extends BusinessException {
    public CommentException(BaseResponseStatus status) {
        super(status);
    }
}
