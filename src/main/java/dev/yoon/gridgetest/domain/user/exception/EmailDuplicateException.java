package dev.yoon.gridgetest.domain.user.exception;


import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class EmailDuplicateException extends BusinessException {

    public EmailDuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
