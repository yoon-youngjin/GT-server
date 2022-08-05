package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.signup.UserJoinService;
import dev.yoon.gridgetest.domain.user.dto.signup.*;
import dev.yoon.gridgetest.domain.user.model.Nickname;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static dev.yoon.gridgetest.global.util.Constants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserSignUpApi {

    private final UserJoinService userJoinService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResult<SignUpDto.Response>> signUp(
            @RequestBody @Valid SignUpDto.Request signUpDto
    ) {

        SignUpDto.Response response = userJoinService.signUpUser(signUpDto);
        return ResponseEntity.ok(ApiUtils.success(response, SIGN_UP));
    }

    @PostMapping("/sms/send")
    public ResponseEntity<ApiResult<Void>> sendSms(
            @RequestBody @Valid SendSmsReq request
    ) {
        userJoinService.sendSms(request);
        return ResponseEntity.ok(ApiUtils.success(null, SEND_SMS));
    }

    @PostMapping("/sms/auth")
    public ResponseEntity<ApiResult<Void>> checkCode(
            @RequestBody @Valid SendSmsAuthReq request
    ) {

        userJoinService.checkCode(request);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_CODE));

    }

    @GetMapping("/duplicated/nickname")
    public ResponseEntity<ApiResult<Void>> checkNickname(
            @Valid NicknameCheckReq request
    ) {
        userJoinService.checkNickname(request);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_NICKNAME));
    }

    @GetMapping("/duplicated/phone")
    public ResponseEntity<ApiResult<Void>> checkPhone(
            @Valid PhoneCheckReq request
    ) {
        userJoinService.checkPhone(request);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_PHONE));
    }


}
