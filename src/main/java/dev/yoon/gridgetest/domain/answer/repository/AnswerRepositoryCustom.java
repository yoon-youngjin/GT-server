package dev.yoon.gridgetest.domain.answer.repository;

import dev.yoon.gridgetest.domain.answer.domain.Answer;
import dev.yoon.gridgetest.domain.answer.dto.GetAnswerRes;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;



public interface AnswerRepositoryCustom {

    Slice<Answer> findAnswersByBoard(Pageable pageable, User user, Board board);

}
