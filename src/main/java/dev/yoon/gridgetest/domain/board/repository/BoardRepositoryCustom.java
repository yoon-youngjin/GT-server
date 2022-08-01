package dev.yoon.gridgetest.domain.board.repository;

import dev.yoon.gridgetest.domain.board.dto.GetMainBoardRes;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;



public interface BoardRepositoryCustom {

    Slice<GetMainBoardRes> findMainBoard(Pageable pageable, User user);

}
