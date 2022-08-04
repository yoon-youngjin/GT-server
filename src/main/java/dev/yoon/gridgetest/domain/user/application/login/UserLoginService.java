package dev.yoon.gridgetest.domain.user.application.login;

import dev.yoon.gridgetest.domain.jwt.application.RefreshTokenRedisService;
import dev.yoon.gridgetest.domain.jwt.application.TokenManager;
import dev.yoon.gridgetest.domain.jwt.dto.TokenDto;
import dev.yoon.gridgetest.domain.jwt.entity.RefreshToken;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.dto.login.LoginDto;
import dev.yoon.gridgetest.domain.user.dto.login.OAuthAttributes;
import dev.yoon.gridgetest.domain.user.dto.login.OauthLoginDto;
import dev.yoon.gridgetest.domain.user.dto.login.OauthSignUpDto;
import dev.yoon.gridgetest.domain.user.dto.signup.SignUpDto;
import dev.yoon.gridgetest.domain.user.exception.LoginFailedException;
import dev.yoon.gridgetest.domain.user.model.UserType;
import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserLoginService {

    private final UserService userService;
    private final TokenManager tokenManager;
    private final RefreshTokenRedisService refreshTokenRedisService;


    @Transactional
    public LoginDto.Response login(LoginDto.Request request) {
        User user = userService.getUserByNicknameOrPhone(request.getId());

        if (!user.getPassword().isMatches(request.getPassword())) {
            throw new LoginFailedException(ErrorCode.LOGIN_ERROR);
        }

        user.updateLoginTime();

        TokenDto tokenDto = tokenManager.createTokenDto(user);
        saveRefreshToken(user, tokenDto);
        return LoginDto.Response.from(tokenDto);

    }

    @Transactional
    public OauthLoginDto.Response loginOauth(String accessToken, OauthLoginDto.Request request) {
        OAuthAttributes oAuthAttributes = getSocialUserInfo(accessToken, UserType.from(request.getUserType()));

        System.out.println(oAuthAttributes.getSocialId());
        Optional<User> user = userService.getUserBySocialId(oAuthAttributes.getSocialId());

        // 회원가입
        if (user.isEmpty()) {
            throw new BusinessException(ErrorCode.PROCEED_WITH_SIGNUP);
        }
        // 로그인
        else {
            user.get().updateLoginTime();
            TokenDto tokenDto = tokenManager.createTokenDto(user.get());
            saveRefreshToken(user.get(), tokenDto);
            return OauthLoginDto.Response.from(tokenDto);

        }

    }

    @Transactional
    public OauthSignUpDto.Response signUpOauth(String accessToken, OauthSignUpDto.Request request) {
        OAuthAttributes oAuthAttributes = getSocialUserInfo(accessToken, UserType.from(request.getUserType()));
        User user = oAuthAttributes.toEntity(request);
        user = userService.register(user);

        user.updateLoginTime();
        //JWT 생성
        TokenDto tokenDto = tokenManager.createTokenDto(user);
        saveRefreshToken(user, tokenDto);
        return OauthSignUpDto.Response.from(tokenDto);

    }

    private OAuthAttributes getSocialUserInfo(String accessToken, UserType userType) {

        SocialLoginApiService socialLoginApiSerivce = SocialLoginApiServiceFactory.getSocialLoginApiService(userType);
        OAuthAttributes oAuthAttributes = socialLoginApiSerivce.getUserInfo(accessToken);
        return oAuthAttributes;

    }

    @Transactional
    private void saveRefreshToken(User user, TokenDto tokenDto) {

        RefreshToken refreshToken = RefreshToken.of(
                user.getPhoneNumber(),
                tokenDto.getRefreshToken(),
                tokenDto.getRefreshTokenExpireTime()
        );
        refreshTokenRedisService.saveRefreshToken(refreshToken);
    }

}
