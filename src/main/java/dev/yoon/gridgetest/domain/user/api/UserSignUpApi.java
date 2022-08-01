package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.signup.UserJoinService;
import dev.yoon.gridgetest.domain.user.dto.signup.NicknameCheckReq;
import dev.yoon.gridgetest.domain.user.dto.signup.SendSmsAuthReq;
import dev.yoon.gridgetest.domain.user.dto.signup.SendSmsReq;
import dev.yoon.gridgetest.domain.user.dto.signup.SignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserSignUpApi {

    private final UserJoinService userJoinService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpDto.Response> signUp(
            @RequestBody @Valid SignUpDto.Request signUpDto
    ) {

        SignUpDto.Response response = userJoinService.signUpUser(signUpDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sms/send")
    public ResponseEntity<Void> sendSms(
            @RequestBody @Valid SendSmsReq request
    ) {
        userJoinService.sendSms(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sms/auth")
    public ResponseEntity<Void> checkCode(
            @RequestBody @Valid SendSmsAuthReq request
    ) {

        userJoinService.checkCode(request);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/duplicated/nickname")
    public ResponseEntity<Void> checkNickname(
            @Valid NicknameCheckReq request
    ) {

        userJoinService.checkNickname(request);
        return ResponseEntity.ok().build();
    }

    //TODO 전화번호 중복 체크


}
