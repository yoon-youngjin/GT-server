package dev.yoon.gridgetest.domain.board.api;

import dev.yoon.gridgetest.domain.board.application.BoardService;
import dev.yoon.gridgetest.domain.board.dto.CreateBoardReq;
import dev.yoon.gridgetest.domain.board.dto.GetMainBoardRes;
import dev.yoon.gridgetest.domain.board.dto.ReportBoardReq;
import dev.yoon.gridgetest.domain.board.dto.UpdateBoardReq;
import dev.yoon.gridgetest.domain.report.dto.ReportServiceReq;
import dev.yoon.gridgetest.global.resolver.UserPhone;
import dev.yoon.gridgetest.global.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApi {


    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> createBoard(
            @Valid CreateBoardReq request,
            @UserPhone String phone
    ) {

        boardService.createBoard(request, phone);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Slice<GetMainBoardRes>> getMainBoard(
            @UserPhone String phone,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                Constants.SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetMainBoardRes> response = boardService.getMainBoardList(pageable, phone);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/{boardId}/like")
    public ResponseEntity<Void> addOrDeleteLike(
            @PathVariable Long boardId,
            @UserPhone String phone
    ) {

        boardService.addOrDeleteLike(boardId, phone);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateBoard(
            @RequestBody @Valid UpdateBoardReq request,
            @UserPhone String phone
    ) {

        boardService.updateBoard(request, phone);
        return ResponseEntity.ok().build();
    }

    @PostMapping("report")
    public ResponseEntity<Void> reportBoard(
            @RequestBody @Valid ReportBoardReq request,
            @UserPhone String phone
    ) {
        boardService.reportBoard(request, phone);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable("boardId") Long boardId,
            @UserPhone String phone
    ) {

        boardService.deleteBoard(boardId, phone);
        return ResponseEntity.ok().build();
    }


}
