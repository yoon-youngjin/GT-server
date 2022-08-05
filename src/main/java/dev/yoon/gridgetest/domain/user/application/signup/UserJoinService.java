package dev.yoon.gridgetest.domain.user.application.signup;

import dev.yoon.gridgetest.domain.authcode.application.AuthCodeService;
import dev.yoon.gridgetest.domain.authcode.entity.AuthCode;
import dev.yoon.gridgetest.global.jwt.application.RefreshTokenRedisService;
import dev.yoon.gridgetest.global.jwt.application.TokenManager;
import dev.yoon.gridgetest.global.jwt.dto.TokenDto;
import dev.yoon.gridgetest.global.jwt.entity.RefreshToken;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.dto.signup.*;
import dev.yoon.gridgetest.infra.email.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserJoinService {

    private final SmsService smsService;
    private final AuthCodeService authCodeService;
    private final UserService userService;
    private final TokenManager tokenManager;
    private final RefreshTokenRedisService refreshTokenRedisService;


    public void sendSms(SendSmsReq request) {

        String authCode = smsService.sendSms(request.getPhone());
        authCodeService.saveAuthCode(AuthCode.of(request.getPhone(), authCode));
    }

    public void checkCode(SendSmsAuthReq request) {
        authCodeService.authenticateCode(request.getPhone(), request.getCode());

    }

    @Transactional
    public SignUpDto.Response signUpUser(SignUpDto.Request signUpDto) {

        User user = signUpDto.toEntity();
        user = userService.register(user);

        user.updateLoginTime();
        //JWT 생성
        TokenDto tokenDto = tokenManager.createTokenDto(user);
        saveRefreshToken(user, tokenDto);

        log.info("[유저 회원가입]/" + user.getNickname().getValue() + "/" + LocalDateTime.now());
        return SignUpDto.Response.from(tokenDto);

    }

    public void checkNickname(NicknameCheckReq request) {
        userService.existsUserByNickname(request.getNickname());

    }

    private void saveRefreshToken(User user, TokenDto tokenDto) {

        RefreshToken refreshToken = RefreshToken.of(
                user.getPhoneNumber(),
                tokenDto.getRefreshToken(),
                tokenDto.getRefreshTokenExpireTime()
        );
        refreshTokenRedisService.saveRefreshToken(refreshToken);
    }

    public void checkPhone(PhoneCheckReq request) {
        userService.existsUserByPhone(request.getPhone());

    }
}
