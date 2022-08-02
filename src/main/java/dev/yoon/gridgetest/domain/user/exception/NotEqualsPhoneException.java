package dev.yoon.gridgetest.domain.user.exception;


import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class NotEqualsPhoneException extends BusinessException {

    public NotEqualsPhoneException(ErrorCode errorCode) {
        super(errorCode);
    }
}
