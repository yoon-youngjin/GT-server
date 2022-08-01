package dev.yoon.gridgetest.domain.user.exception;

import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class NicknameDuplicateException extends BusinessException {

    public NicknameDuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
