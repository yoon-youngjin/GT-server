package dev.yoon.gridgetest.domain.admin.api.board;

import dev.yoon.gridgetest.domain.admin.application.board.AdminBoardService;
import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardDetailRes;
import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardInfoDto;
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
@RequestMapping("/api/admin/board")
public class AdminBoardApi {

    private final AdminBoardService adminBoardService;

    @Operation(summary = "관리자 - 피드 전체 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping
    public ResponseEntity<ApiResult<Page<GetBoardInfoDto.Response>>> getAllBoard(
            @Valid GetBoardInfoDto.Request request,
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), SET_PAGE_ITEM_MAX_COUNT);

        Page<GetBoardInfoDto.Response> response = adminBoardService.getAllBoard(pageable, request);
        return ResponseEntity.ok(ApiUtils.success(response, GET_ALL_BOARD));
    }

    @Operation(summary = "관리자 - 피드 상세 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<ApiResult<GetBoardDetailRes>> getBoardDetailInfo(
            @PathVariable("boardId") Long boardId
    ) {

        GetBoardDetailRes response = adminBoardService.getBoardDetailInfo(boardId);
        return ResponseEntity.ok(ApiUtils.success(response, GET_BOARD_DETAIL_INFO));
    }

    @Operation(summary = "관리자 - 피드 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResult<Void>> deleteBoard(
            @PathVariable("boardId") Long boardId
    ) {

        adminBoardService.deleteBoard(boardId);
        return ResponseEntity.ok(ApiUtils.success(null, FORCE_DELETE_BOARD));
    }


}
