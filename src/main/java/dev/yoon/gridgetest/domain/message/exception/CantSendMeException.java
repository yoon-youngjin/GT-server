package dev.yoon.gridgetest.domain.message.exception;


import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class CantSendMeException extends BusinessException {

    public CantSendMeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
