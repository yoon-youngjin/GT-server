package dev.yoon.gridgetest.domain.user.exception;


import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class LoginFailedException extends BusinessException {

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
