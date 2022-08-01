package dev.yoon.gridgetest.domain.board.repository;


import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.domain.Like;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LikeRepository extends JpaRepository<Like, Long> {

    Boolean existsByBoardAndUser(Board board, User user);

    Optional<Like> findByBoardAndUser(Board board, User user);

}
