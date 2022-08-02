package dev.yoon.gridgetest.domain.user.exception;


import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class NotEqualsCheckPasswordException extends BusinessException {

    public NotEqualsCheckPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
