package dev.yoon.gridgetest.domain.board.api;

import dev.yoon.gridgetest.domain.board.application.BoardService;
import dev.yoon.gridgetest.domain.board.dto.CreateBoardReq;
import dev.yoon.gridgetest.domain.board.dto.GetMainBoardRes;
import dev.yoon.gridgetest.domain.board.dto.ReportBoardReq;
import dev.yoon.gridgetest.domain.board.dto.UpdateBoardReq;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardApi {

    private final BoardService boardService;

    @Operation(summary = "피드 생성")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PostMapping
    public ResponseEntity<ApiResult<Void>> createBoard(
            @Valid CreateBoardReq request,
            @UserPhone String phone
    ) {

        boardService.createBoard(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CREATE_BOARD));
    }

    @Operation(summary = "피드 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @GetMapping
    public ResponseEntity<ApiResult<Slice<GetMainBoardRes>>> getMainBoard(
            @UserPhone String phone,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(
                page.orElse(0),
                Constants.SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetMainBoardRes> response = boardService.getMainBoardList(pageable, phone);
        return ResponseEntity.ok(ApiUtils.success(response, GET_MAIN_BOARD));

    }

    @Operation(summary = "댓글 좋아요 추가 또는 취소")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PostMapping("/{boardId}/like")
    public ResponseEntity<ApiResult<Void>> addOrDeleteLike(
            @PathVariable Long boardId,
            @UserPhone String phone
    ) {

        boardService.addOrDeleteLike(boardId, phone);
        return ResponseEntity.ok(ApiUtils.success(null, ADD_OR_DELETE_LIKE));
    }

    @Operation(summary = "피드 수정")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @PatchMapping
    public ResponseEntity<ApiResult<Void>> updateBoard(
            @RequestBody @Valid UpdateBoardReq request,
            @UserPhone String phone
    ) {

        boardService.updateBoard(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, UPDATE_BOARD));

    }


    @Operation(summary = "피드 삭제")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", defaultValue ="jwt access token", dataType = "string", value = "jwt access token", required = true, paramType = "header")
    })
    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResult<Void>> deleteBoard(
            @PathVariable("boardId") Long boardId,
            @UserPhone String phone
    ) {

        boardService.deleteBoard(boardId, phone);
        return ResponseEntity.ok(ApiUtils.success(null, DELETE_BOARD));

    }


}
