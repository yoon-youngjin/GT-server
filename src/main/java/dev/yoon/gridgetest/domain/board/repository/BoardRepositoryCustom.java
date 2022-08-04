package dev.yoon.gridgetest.domain.board.repository;

import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardInfoDto;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.dto.GetMainBoardRes;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;



public interface BoardRepositoryCustom {

    Slice<GetMainBoardRes> findMainBoard(Pageable pageable, User user);

    Page<Board> findAllBoardByQuery(Pageable pageable, GetBoardInfoDto.Request request);
}
