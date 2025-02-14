package me.jooeon.mybeauty.global.common.exception.exception.article;

import me.jooeon.mybeauty.global.common.exception.exception.BusinessException;
import me.jooeon.mybeauty.global.common.model.enums.BaseResponseStatus;

public class ArticleException extends BusinessException {
    public ArticleException(BaseResponseStatus status) {
        super(status);
    }
}
