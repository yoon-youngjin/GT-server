package dev.yoon.gridgetest.domain.user.api;

import dev.yoon.gridgetest.domain.user.application.info.UserInfoService;
import dev.yoon.gridgetest.domain.user.dto.info.*;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import dev.yoon.gridgetest.global.util.ApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
public class UserInfoApi {

    private final UserInfoService userInfoService;

    @Operation(summary = "유저 정보 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping
    public ResponseEntity<ApiResult<GetUserInfoRes>> getUserInfo(
            @UserPhone String phone
    ){
        GetUserInfoRes response = userInfoService.getUserInfo(phone);
        return ResponseEntity.ok(ApiUtils.success(response, GET_USER_INFO));
    }

    @Operation(summary = "번호로 유저 검증")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping("/check")
    public ResponseEntity<ApiResult<Void>> checkUserByPhone(
            @RequestBody @Valid CheckUserReq request,
            @UserPhone String phone
    ){
        userInfoService.checkUserByPhone(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_USER_BY_PHONE));
    }

    @Operation(summary = "유저 비밀번호 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PatchMapping("/password")
    public ResponseEntity<ApiResult<Void>> updatePassword(
            @RequestBody @Valid UpdatePasswordReq request,
            @UserPhone String phone
    ){
        userInfoService.updatePassword(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, UPDATE_PASSWORD));
    }

    @Operation(summary = "이름 변경 기간 검증")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping("/check/name")
    public ResponseEntity<ApiResult<Void>> checkUpdateTimeName(
            @Valid CheckUpdateNameReq request,
            @UserPhone String phone
    ) {
        userInfoService.checkUpdateTimeName(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CHECK_UPDATE_TIME_NAME));
    }

    @Operation(summary = "닉네임 변경 기간 검증")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping("/check/nickname")
    public ResponseEntity<ApiResult<Void>> checkUpdateTimeNickname(
            @Valid CheckUpdateNicknameReq request,
            @UserPhone String phone
    ) {
        userInfoService.checkUpdateTimeNickname(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CHECKU_UPDATE_TIME_NICKNAME));
    }

    @Operation(summary = "유저 정보 변경")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PatchMapping
    public ResponseEntity<ApiResult<Void>> updateUserInfo(
            @Valid UpdateUserInfoReq request,
            @UserPhone String phone
    ) {
        userInfoService.updateUserInfo(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, UPDATE_USER_INFO));


    }

    @Operation(summary = "유저 비공개 계정 전환")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PatchMapping("/private")
    public ResponseEntity<ApiResult<Void>> updatePrivateUser(
            @UserPhone String phone
    ) {
        userInfoService.updatePrivate(phone);
        return ResponseEntity.ok(ApiUtils.success(null, UPDATE_PRIVATE_USER));

    }

    @Operation(summary = "유저 탙퇴")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @DeleteMapping("/quit")
    public ResponseEntity<ApiResult<Void>> quitUser(
            @UserPhone String phone

    ){
        userInfoService.quitUser(phone);
        return ResponseEntity.ok(ApiUtils.success(null, QUIT_USER));


    }


}
