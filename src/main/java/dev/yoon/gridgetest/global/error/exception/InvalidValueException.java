package dev.yoon.gridgetest.global.error.exception;


public class InvalidValueException extends BusinessException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }

}