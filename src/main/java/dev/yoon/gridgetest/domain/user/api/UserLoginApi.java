package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.login.UserLoginService;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.dto.login.LoginDto;
import dev.yoon.gridgetest.domain.user.dto.login.OauthLoginDto;
import dev.yoon.gridgetest.domain.user.dto.login.OauthSignUpDto;
import dev.yoon.gridgetest.global.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserLoginApi {

    private final UserLoginService userLoginService;
    private final TokenValidator tokenValidator;

    @PostMapping("/login")
    public ResponseEntity<LoginDto.Response> login(
            @RequestBody @Valid LoginDto.Request request
    ) {

        LoginDto.Response response = userLoginService.login(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/oauth/login")
    public ResponseEntity<OauthLoginDto.Response> oauthLogin(
            @RequestBody @Valid OauthLoginDto.Request requestDto,
            HttpServletRequest request) {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        tokenValidator.validateAuthorization(authorization);
        tokenValidator.validateMemberType(requestDto.getUserType());

        String accessToken = authorization.split(" ")[1];

        OauthLoginDto.Response response = userLoginService.loginOauth(accessToken, requestDto);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/oauth/sign-up")
    public ResponseEntity<OauthSignUpDto.Response> oauthSignUp(
            @RequestBody @Valid OauthSignUpDto.Request requestDto,
            HttpServletRequest request
    ){
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        tokenValidator.validateAuthorization(authorization);
        tokenValidator.validateMemberType(requestDto.getUserType());

        String accessToken = authorization.split(" ")[1];

        OauthSignUpDto.Response response = userLoginService.signUpOauth(accessToken, requestDto);
        return ResponseEntity.ok(response);

    }

}
