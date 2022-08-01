package dev.yoon.gridgetest.domain.jwt.application;

import dev.yoon.gridgetest.domain.jwt.entity.RefreshToken;
import dev.yoon.gridgetest.domain.jwt.repository.RefreshTokenRedisRepository;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public void saveRefreshToken(RefreshToken refreshToken){
        refreshTokenRedisRepository.save(refreshToken);
    }

    public RefreshToken getRefreshTokenByEmail(String email){
        RefreshToken refreshToken = refreshTokenRedisRepository.findById(email)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        return refreshToken;
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRedisRepository.deleteById(email);
    }
}
