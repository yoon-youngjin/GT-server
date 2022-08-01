package dev.yoon.gridgetest.global.error.exception;

public class ForbiddenException extends BusinessException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
