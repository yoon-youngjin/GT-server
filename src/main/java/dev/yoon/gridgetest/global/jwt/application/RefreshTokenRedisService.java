package dev.yoon.gridgetest.global.jwt.application;

import dev.yoon.gridgetest.global.error.exception.AuthenticationException;
import dev.yoon.gridgetest.global.jwt.entity.RefreshToken;
import dev.yoon.gridgetest.global.jwt.repository.RefreshTokenRedisRepository;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public void saveRefreshToken(RefreshToken refreshToken){
        refreshTokenRedisRepository.save(refreshToken);
    }

    public RefreshToken getRefreshTokenByPhone(String phone){
        RefreshToken refreshToken = refreshTokenRedisRepository.findById(phone)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        return refreshToken;
    }

    public void validateRefreshTokenExpirationTime(LocalDateTime refreshTokenExpirationTime, LocalDateTime now) {
        if(refreshTokenExpirationTime.isBefore(now)) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRedisRepository.deleteById(email);
    }
}
