package dev.yoon.gridgetest.domain.board.repository;

import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    Long countBoardByUser(User user);

    @Query("select b from Board b " +
            "where b.user=:user and b.isDeleted is false")
    Slice<Board> findAllByUser(Pageable pageable, User user);
}
