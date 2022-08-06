package dev.yoon.gridgetest.domain.follow.api;

import dev.yoon.gridgetest.domain.follow.application.FollowService;
import dev.yoon.gridgetest.domain.follow.dto.FollowUserReq;
import dev.yoon.gridgetest.domain.follow.dto.GetRequestFollowRes;
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
import java.util.List;

import static dev.yoon.gridgetest.global.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowApi {

    private final FollowService followService;

    @Operation(summary = "팔로우 추가 또는 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PostMapping
    public ResponseEntity<ApiResult<Void>> follow(
            @RequestBody @Valid FollowUserReq request,
            @UserPhone String phone
    ) {

        followService.follow(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, FOLLOW));
    }

    @Operation(summary = "팔로우 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping
    public ResponseEntity<ApiResult<List<GetRequestFollowRes>>> getRequestFollow(
            @UserPhone String phone
    ){
        List<GetRequestFollowRes> response = followService.getRequestFollow(phone);
        return ResponseEntity.ok(ApiUtils.success(response, GET_REQUEST_FOLLOW));
    }

    @Operation(summary = "팔로우 수락")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PatchMapping("/{followId}/accept")
    public ResponseEntity<ApiResult<Void>> acceptFollow(
            @PathVariable("followId") Long followId,
            @UserPhone String phone
    ){
        followService.acceptFollow(followId, phone);
        return ResponseEntity.ok(ApiUtils.success(null, ACCEPT_FOLLOW));

    }

    @Operation(summary = "팔로우 거절")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @DeleteMapping("/{followId}/reject")
    public ResponseEntity<ApiResult<Void>> rejectFollow(
            @PathVariable("followId") Long followId,
            @UserPhone String phone
    ){
        followService.rejectFollow(followId, phone);
        return ResponseEntity.ok(ApiUtils.success(null, REJECT_FOLLOW));


    }


}
