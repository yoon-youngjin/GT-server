package dev.yoon.gridgetest.infra.email.exception;


import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class FailedToSendSmsException extends BusinessException {

    public FailedToSendSmsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
