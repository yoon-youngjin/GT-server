package dev.yoon.gridgetest.domain.authcode.exception;


import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class AuthCodeNotEqualException extends EntityNotFoundException {

    public AuthCodeNotEqualException(ErrorCode errorCode) {
        super(errorCode);
    }
}
