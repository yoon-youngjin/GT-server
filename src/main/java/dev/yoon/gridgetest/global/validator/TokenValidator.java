package dev.yoon.gridgetest.global.validator;

import dev.yoon.gridgetest.global.jwt.constant.GrantType;
import dev.yoon.gridgetest.domain.user.model.UserType;
import dev.yoon.gridgetest.global.error.exception.AuthenticationException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import dev.yoon.gridgetest.global.error.exception.InvalidValueException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Service
public class TokenValidator {

    public void validateAuthorization(String authorizationHeader) {

        // 1. 토큰 유무 확인
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2. Bearer Grant Type 확인
        String[] authorizations = authorizationHeader.split(" ");
        if(authorizations.length < 2 || (!GrantType.BEARRER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }

    public void validateMemberType(String userType) {
        if(!UserType.isUserType(userType)) {
            throw new InvalidValueException(ErrorCode.INVALID_USER_TYPE);
        }
    }


}
