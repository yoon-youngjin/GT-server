package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.info.UserInfoService;
import dev.yoon.gridgetest.domain.user.dto.info.*;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import dev.yoon.gridgetest.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static dev.yoon.gridgetest.global.util.Constants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserInfoApi {

    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<ApiResult<GetUserInfoRes>> getUserInfo(
            @UserPhone String phone
    ){
        GetUserInfoRes response = userInfoService.getUserInfo(phone);
        return ResponseEntity.ok(ApiUtils.success(response, GET_USER_INFO));
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResult<Void>> checkUserByPhone(
            @RequestBody @Valid CheckUserReq request,
            @UserPhone String phone
    ){
        userInfoService.checkUserByPhone(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_USER_BY_PHONE));
    }

    // 비밀번호 변경
    @PatchMapping("/password")
    public ResponseEntity<ApiResult<Void>> updatePassword(
            @RequestBody @Valid UpdatePasswordReq request,
            @UserPhone String phone
    ){
        userInfoService.updatePassword(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, UPDATE_PASSWORD));
    }

    // 이름 변경 체크 - 2주 내에 2번
    @GetMapping("/check/name")
    public ResponseEntity<ApiResult<Void>> checkUpdateTimeName(
            @Valid CheckUpdateNameReq request,
            @UserPhone String phone
    ) {
        userInfoService.checkUpdateTimeName(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_UPDATE_TIME_NAME));
    }

    // 닉네임 변경 체크 - 2주 내에 2번
    @GetMapping("/check/nickname")
    public ResponseEntity<ApiResult<Void>> checkUpdateTimeNickname(
            @Valid CheckUpdateNicknameReq request,
            @UserPhone String phone
    ) {
        userInfoService.checkUpdateTimeNickname(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CHECKU_UPDATE_TIME_NICKNAME));
    }

    // 유저 정보 변경
    @PatchMapping
    public ResponseEntity<ApiResult<Void>> updateUserInfo(
            @Valid UpdateUserInfoReq request,
            @UserPhone String phone
    ) {
        userInfoService.updateUserInfo(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, UPDATE_USER_INFO));


    }

    @PatchMapping("/private")
    public ResponseEntity<ApiResult<Void>> updatePrivateUser(
            @UserPhone String phone
    ) {
        userInfoService.updatePrivate(phone);
        return ResponseEntity.ok(ApiUtils.success(null, UPDATE_PRIVATE_USER));

    }

    @DeleteMapping("/quit")
    public ResponseEntity<ApiResult<Void>> quitUser(
            @UserPhone String phone

    ){
        userInfoService.quitUser(phone);
        return ResponseEntity.ok(ApiUtils.success(null, QUIT_USER));


    }


}
