package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.info.UserInfoService;
import dev.yoon.gridgetest.domain.user.dto.info.*;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserInfoApi {

    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<GetUserInfoRes> getUserInfo(
            @UserPhone String phone
    ){
        GetUserInfoRes response = userInfoService.getUserInfo(phone);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check")
    public ResponseEntity<Void> checkUser(
            @RequestBody @Valid CheckUserReq request,
            @UserPhone String phone
    ){
        userInfoService.checkUser(request, phone);
        return ResponseEntity.ok().build();
    }

    // 비밀번호 변경
    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @RequestBody @Valid UpdatePasswordReq request,
            @UserPhone String phone
    ){
        userInfoService.updatePassword(request, phone);
        return ResponseEntity.ok().build();
    }

    // 이름 변경 체크
    @GetMapping("/check/name")
    public ResponseEntity<Void> checkUpdateName(
            @Valid CheckUpdateNameReq request,
            @UserPhone String phone
    ) {
        userInfoService.checkUpdateName(phone);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check/nickname")
    public ResponseEntity<Void> checkUpdateNickname(
            @Valid CheckUpdateNicknameReq request,
            @UserPhone String phone
    ) {
        userInfoService.checkUpdateNickname(request, phone);
        return ResponseEntity.ok().build();
    }

    // 유저 정보 변경
    @PatchMapping
    public ResponseEntity<Void> updateUserInfo(
            @Valid UpdateUserInfoReq request,
            @UserPhone String phone
    ) {
        userInfoService.updateUserInfo(request, phone);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/private")
    public ResponseEntity<Void> updatePrivateUser(
            @UserPhone String phone
    ) {
        userInfoService.updatePrivate(phone);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/quit")
    public ResponseEntity<Void> quitUser(
            @UserPhone String phone

    ){
        userInfoService.quitUser(phone);
        return ResponseEntity.ok().build();

    }


}
