package dev.yoon.gridgetest.web.kakaotoken.exception;

import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TokenNotFoundException extends BusinessException {

    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }


}
