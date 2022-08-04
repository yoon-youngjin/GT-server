package dev.yoon.gridgetest.domain.admin.application.board;

import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardDetailRes;
import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardInfoDto;
import dev.yoon.gridgetest.domain.board.application.BoardService;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.model.UserState;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminBoardService {

    private final BoardService boardService;


    public Page<GetBoardInfoDto.Response> getAllBoard(Pageable pageable, GetBoardInfoDto.Request request) {
        Page<Board> boards =  boardService.getAllBoardByQuery(pageable, request);
        List<GetBoardInfoDto.Response> collect = boards.stream()
                .map(board -> GetBoardInfoDto.Response.of(board, board.getUser())).collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, boards.getTotalElements());

    }

    public GetBoardDetailRes getBoardDetailInfo(Long boardId) {

        Board board = boardService.getBoardById(boardId);
        return GetBoardDetailRes.from(board);
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardService.deleteBoardByAdmin(boardId);

    }

}
