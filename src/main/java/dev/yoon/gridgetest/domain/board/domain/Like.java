package dev.yoon.gridgetest.domain.board.domain;

import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "BOARD_LIKE")
@Getter
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_LIKE_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "BOARD_ID", nullable = false)
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
