package dev.yoon.gridgetest.domain.user.exception;


import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class isQuitUserException extends BusinessException {

    public isQuitUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
