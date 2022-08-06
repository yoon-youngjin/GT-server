package dev.yoon.gridgetest.domain.user.application.token;

import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.dto.token.AccessTokenRes;
import dev.yoon.gridgetest.domain.user.exception.LoginFailedException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import dev.yoon.gridgetest.global.jwt.application.RefreshTokenRedisService;
import dev.yoon.gridgetest.global.jwt.application.TokenManager;
import dev.yoon.gridgetest.global.jwt.entity.RefreshToken;
import dev.yoon.gridgetest.global.util.DateTimeUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

    private final TokenManager tokenManager;
    private final UserService userService;
    private final RefreshTokenRedisService refreshTokenRedisService;

    public AccessTokenRes updateAccessToken(String refreshToken, LocalDateTime now) {

        Claims tokenClaims = tokenManager.getTokenClaims(refreshToken);

        String phone = tokenClaims.getAudience();
        RefreshToken refreshTokenByPhone = refreshTokenRedisService.getRefreshTokenByPhone(phone);

        refreshTokenRedisService.validateRefreshTokenExpirationTime(
                DateTimeUtils.convertToLocalDateTime(refreshTokenByPhone.getExpiration()), now
        );

        User user = userService.getUserByPhoneNumber(phone);

        if (user.getIsDeleted()) {
            throw new LoginFailedException(ErrorCode.QUIT_USER);
        }
        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(user, accessTokenExpireTime);

        return AccessTokenRes.of(accessToken, accessTokenExpireTime);

    }

}
