package dev.yoon.gridgetest.domain.user.exception;


import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class NotValidTokenException extends BusinessException {

    public NotValidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

}