package dev.yoon.gridgetest.domain.authcode.application;

import dev.yoon.gridgetest.domain.authcode.entity.AuthCode;
import dev.yoon.gridgetest.domain.authcode.exception.AuthCodeNotEqualException;
import dev.yoon.gridgetest.domain.authcode.repository.AuthCodeRedisRepository;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthCodeService {

    private final AuthCodeRedisRepository authCodeRedisRepository;

    public void saveAuthCode(AuthCode code){
        authCodeRedisRepository.save(code);
    }

    public AuthCode getAuthCodeByEmail(String email){
        AuthCode authCode = authCodeRedisRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.AUTH_CODE_NOT_FOUND));
        return authCode;
    }

    public void deleteAuthCode(String email) {
        authCodeRedisRepository.deleteById(email);
    }

    public void authenticateCode(String email, String code) {
        AuthCode authCode = getAuthCodeByEmail(email);

        if (!authCode.getCode().equals(code)) {
            throw new AuthCodeNotEqualException(ErrorCode.AUTH_CODE_NOT_EQUAL);
        }

        deleteAuthCode(email);

    }

}
