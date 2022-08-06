package dev.yoon.gridgetest.domain.user.api;


import dev.yoon.gridgetest.domain.user.application.activity.UserActivityService;
import dev.yoon.gridgetest.domain.user.dto.activity.GetUserBoardRes;
import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import dev.yoon.gridgetest.global.util.ApiUtils;
import dev.yoon.gridgetest.global.util.Constants;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.GET_USER_BOARD;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserActivityApi {

    private final UserActivityService userActivityService;

    @Operation(summary = "유저 피드 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping("/board")
    public ResponseEntity<ApiResult<Slice<GetUserBoardRes>>> getUserBoard(
            @UserPhone String phone,
            Optional<Integer> page
    ){
        Pageable pageable = PageRequest.of(
                page.orElse(0),
                Constants.SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetUserBoardRes> response = userActivityService.getUserBoard(pageable, phone);
        return ResponseEntity.ok(ApiUtils.success(response, GET_USER_BOARD));
    }

}
