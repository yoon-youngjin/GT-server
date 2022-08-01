package dev.yoon.gridgetest.domain.board.domain;

import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "board_like")
@Getter
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder
    public Like(Board board, User user) {
        this.board = board;
        this.user = user;
    }

    public static Like createLike(Board board, User user) {
        return Like.builder()
                .board(board)
                .user(user)
                .build();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
