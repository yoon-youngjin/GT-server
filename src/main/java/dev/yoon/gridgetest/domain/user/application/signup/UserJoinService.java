package dev.yoon.gridgetest.domain.user.application.signup;

import dev.yoon.gridgetest.domain.authcode.application.AuthCodeService;
import dev.yoon.gridgetest.domain.authcode.entity.AuthCode;
import dev.yoon.gridgetest.domain.jwt.application.RefreshTokenRedisService;
import dev.yoon.gridgetest.domain.jwt.application.TokenManager;
import dev.yoon.gridgetest.domain.jwt.dto.TokenDto;
import dev.yoon.gridgetest.domain.jwt.entity.RefreshToken;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.dto.signup.NicknameCheckReq;
import dev.yoon.gridgetest.domain.user.dto.signup.SendSmsAuthReq;
import dev.yoon.gridgetest.domain.user.dto.signup.SendSmsReq;
import dev.yoon.gridgetest.domain.user.dto.signup.SignUpDto;
import dev.yoon.gridgetest.domain.user.model.Nickname;
import dev.yoon.gridgetest.domain.user.validator.UserValidator;
import dev.yoon.gridgetest.infra.email.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return SignUpDto.Response.from(tokenDto);

    }

    public void checkNickname(NicknameCheckReq request) {
        userService.existsUserByNickname(Nickname.from(request.getNickname()));

    }

    private void saveRefreshToken(User user, TokenDto tokenDto) {

        RefreshToken refreshToken = RefreshToken.of(
                user.getPhoneNumber(),
                tokenDto.getRefreshToken(),
                tokenDto.getRefreshTokenExpireTime()
        );
        refreshTokenRedisService.saveRefreshToken(refreshToken);
    }
}
