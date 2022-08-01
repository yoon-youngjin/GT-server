package dev.yoon.gridgetest.domain.board.repository;

import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Long countBoardByUser(User user);

    List<Board> findAllByUser(User user);
}
