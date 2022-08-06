package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.signup.UserJoinService;
import dev.yoon.gridgetest.domain.user.dto.signup.*;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.util.ApiUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "유저 회원가입")
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResult<SignUpDto.Response>> signUp(
            @RequestBody @Valid SignUpDto.Request signUpDto
    ) {

        SignUpDto.Response response = userJoinService.signUpUser(signUpDto);
        return ResponseEntity.ok(ApiUtils.success(response, SIGN_UP));
    }

    @Operation(summary = "인증 문자 전송")
    @PostMapping("/sms/send")
    public ResponseEntity<ApiResult<Void>> sendSms(
            @RequestBody @Valid SendSmsReq request
    ) {
        userJoinService.sendSms(request);
        return ResponseEntity.ok(ApiUtils.success(null, SEND_SMS));
    }

    @Operation(summary = "인증 문자 검증")
    @PostMapping("/sms/auth")
    public ResponseEntity<ApiResult<Void>> checkCode(
            @RequestBody @Valid SendSmsAuthReq request
    ) {

        userJoinService.checkCode(request);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_CODE));

    }

    @Operation(summary = "닉네임 중복 검증")
    @GetMapping("/duplicated/nickname")
    public ResponseEntity<ApiResult<Void>> checkNickname(
            @Valid NicknameCheckReq request
    ) {
        userJoinService.checkNickname(request);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_NICKNAME));
    }

    @Operation(summary = "번호 중복 검증")
    @GetMapping("/duplicated/phone")
    public ResponseEntity<ApiResult<Void>> checkPhone(
            @Valid PhoneCheckReq request
    ) {
        userJoinService.checkPhone(request);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_PHONE));
    }


}
