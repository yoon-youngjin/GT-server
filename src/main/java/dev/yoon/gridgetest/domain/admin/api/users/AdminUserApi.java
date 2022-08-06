package dev.yoon.gridgetest.domain.admin.api.users;

import dev.yoon.gridgetest.domain.admin.application.users.AdminUserService;
import dev.yoon.gridgetest.domain.admin.dto.users.GetUserDetailRes;
import dev.yoon.gridgetest.domain.admin.dto.users.GetUserInfoDto;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.util.ApiUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.*;
import static dev.yoon.gridgetest.global.util.Constants.SET_PAGE_ITEM_MAX_COUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserApi {

    private final AdminUserService adminUserService;

    @Operation(summary = "관리자 - 유저 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping
    public ResponseEntity<ApiResult<Page<GetUserInfoDto.Response>>> getAllUserInfo(
            @Valid GetUserInfoDto.Request request,
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, SET_PAGE_ITEM_MAX_COUNT);

        Page<GetUserInfoDto.Response> response = adminUserService.getUsersInfo(pageable, request);
        return ResponseEntity.ok(ApiUtils.success(response, GET_ALL_USER_INFO));
    }

    @Operation(summary = "관리자 - 유저 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResult<GetUserDetailRes>> getUserDetailInfo(
            @PathVariable("userId") Long userId
    ) {

        GetUserDetailRes response = adminUserService.getUserDetailInfo(userId);
        return ResponseEntity.ok(ApiUtils.success(response, GET_USER_DETAIL_INFO));
    }

    @Operation(summary = "관리자 - 유저 정지")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResult<Void>> pauseUser(
            @PathVariable("userId") Long userId
    ) {

        adminUserService.pauseUser(userId);
        return ResponseEntity.ok(ApiUtils.success(null, PAUSE_USER));
    }


}
