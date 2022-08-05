package dev.yoon.gridgetest.domain.user.application.activity;

import dev.yoon.gridgetest.domain.board.application.BoardService;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.dto.activity.GetUserBoardRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserActivityService {

    private final UserService userService;
    private final BoardService boardService;

    public Slice<GetUserBoardRes> getUserBoard(Pageable pageable, String phone) {

        User user = userService.getUserByPhoneNumber(phone);
        Slice<Board> boards = boardService.getBoardByUser(pageable, user);
        List<GetUserBoardRes> results = boards.stream().map(GetUserBoardRes::from).collect(Collectors.toList());
        return new SliceImpl<>(results, pageable, boards.hasNext());

    }

}
