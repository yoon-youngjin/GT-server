package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.login.UserLoginService;
import dev.yoon.gridgetest.domain.user.dto.login.LoginDto;
import dev.yoon.gridgetest.domain.user.dto.login.OauthLoginDto;
import dev.yoon.gridgetest.domain.user.dto.login.OauthSignUpDto;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.util.ApiUtils;
import dev.yoon.gridgetest.global.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.time.LocalDateTime;

import static dev.yoon.gridgetest.global.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserLoginApi {

    private final UserLoginService userLoginService;
    private final TokenValidator tokenValidator;

    @PostMapping("/auto-login")
    public ResponseEntity<ApiResult<LoginDto.Response>> autoLogin(
            HttpServletRequest request
    ) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        tokenValidator.validateAuthorization(authorization);

        String refreshToken = authorization.split(" ")[1];

        LoginDto.Response response = userLoginService.autoLogin(refreshToken, LocalDateTime.now());
        return ResponseEntity.ok(ApiUtils.success(response, LOGIN));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResult<LoginDto.Response>> login(
            @RequestBody @Valid LoginDto.Request request
    ) {
        LoginDto.Response response = userLoginService.login(request);
        return ResponseEntity.ok(ApiUtils.success(response, LOGIN));

    }

    @PostMapping("/oauth/login")
    public ResponseEntity<ApiResult<OauthLoginDto.Response>> oauthLogin(
            @RequestBody @Valid OauthLoginDto.Request requestDto,
            HttpServletRequest request) {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        tokenValidator.validateAuthorization(authorization);
        tokenValidator.validateMemberType(requestDto.getUserType());

        String accessToken = authorization.split(" ")[1];

        OauthLoginDto.Response response = userLoginService.loginOauth(accessToken, requestDto);

        return ResponseEntity.ok(ApiUtils.success(response, OAUTH_LOGIN));

    }

    @PostMapping("/oauth/sign-up")
    public ResponseEntity<ApiResult<OauthSignUpDto.Response>> oauthSignUp(
            @RequestBody @Valid OauthSignUpDto.Request requestDto,
            HttpServletRequest request
    ){
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        tokenValidator.validateAuthorization(authorization);
        tokenValidator.validateMemberType(requestDto.getUserType());

        String accessToken = authorization.split(" ")[1];

        OauthSignUpDto.Response response = userLoginService.signUpOauth(accessToken, requestDto);
        return ResponseEntity.ok(ApiUtils.success(response, OAUTH_SIGN_UP));

    }

}
