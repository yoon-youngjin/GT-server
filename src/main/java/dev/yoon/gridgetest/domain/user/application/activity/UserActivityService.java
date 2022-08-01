package dev.yoon.gridgetest.domain.user.application.activity;

import dev.yoon.gridgetest.domain.board.application.BoardService;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.dto.activity.GetUserBoardRes;
import dev.yoon.gridgetest.domain.user.dto.info.GetUserInfoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserActivityService {

    private final UserService userService;
    private final BoardService boardService;

    public List<GetUserBoardRes> getUserBoard(String phone) {

        User user = userService.getUserByPhoneNumber(phone);
        List<Board> boards = boardService.getBoardByUser(user);

        return boards.stream().map(GetUserBoardRes::from).collect(Collectors.toList());

    }

}
