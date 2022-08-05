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

    @PostMapping
    public ResponseEntity<ApiResult<Void>> createBoard(
            @Valid CreateBoardReq request,
            @UserPhone String phone
    ) {

        boardService.createBoard(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, CREATE_BOARD));
    }

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

    @PostMapping("/{boardId}/like")
    public ResponseEntity<ApiResult<Void>> addOrDeleteLike(
            @PathVariable Long boardId,
            @UserPhone String phone
    ) {

        boardService.addOrDeleteLike(boardId, phone);
        return ResponseEntity.ok(ApiUtils.success(null, ADD_OR_DELETE_LIKE));
    }

    @PatchMapping
    public ResponseEntity<ApiResult<Void>> updateBoard(
            @RequestBody @Valid UpdateBoardReq request,
            @UserPhone String phone
    ) {

        boardService.updateBoard(request, phone);
        return ResponseEntity.ok(ApiUtils.success(null, UPDATE_BOARD));

    }



    @DeleteMapping("/{boardId}")
    public ResponseEntity<ApiResult<Void>> deleteBoard(
            @PathVariable("boardId") Long boardId,
            @UserPhone String phone
    ) {

        boardService.deleteBoard(boardId, phone);
        return ResponseEntity.ok(ApiUtils.success(null, DELETE_BOARD));

    }


}
