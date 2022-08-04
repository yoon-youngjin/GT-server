package dev.yoon.gridgetest.domain.admin.api.board;

import dev.yoon.gridgetest.domain.admin.application.board.AdminBoardService;
import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardDetailRes;
import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static dev.yoon.gridgetest.global.util.Constants.SET_PAGE_ITEM_MAX_COUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/boards")
public class AdminBoardApi {

    private final AdminBoardService adminBoardService;

    @GetMapping
    public ResponseEntity<Page<GetBoardInfoDto.Response>> getAllBoard(
            @Valid GetBoardInfoDto.Request request,
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(page.orElse(0), SET_PAGE_ITEM_MAX_COUNT);

        Page<GetBoardInfoDto.Response> response = adminBoardService.getAllBoard(pageable, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<GetBoardDetailRes> getUserDetailInfo(
            @PathVariable("boardId") Long boardId
    ) {

        GetBoardDetailRes response = adminBoardService.getBoardDetailInfo(boardId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable("boardId") Long boardId
    ){

        adminBoardService.deleteBoard(boardId);
        return ResponseEntity.ok().build();
    }



}
