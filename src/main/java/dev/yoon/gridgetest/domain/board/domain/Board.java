package dev.yoon.gridgetest.domain.board.domain;

import dev.yoon.gridgetest.domain.answer.domain.Answer;
import dev.yoon.gridgetest.domain.base.BaseTimeEntity;
import dev.yoon.gridgetest.domain.board.model.BoardState;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOARD")
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Lob
    @Column(length = 200, nullable = false)
    private String content;

    @OneToMany(
            mappedBy = "board",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Answer> answers = new ArrayList<>();


    @OneToMany(
            mappedBy = "board",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<BoardImage> boardImages = new ArrayList<>();

    @OneToMany(
            mappedBy = "board",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Like> likeList = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "TINYINT")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BoardState boardState;

    @Builder
    public Board(String content, User user) {
        this.content = content;
        this.user = user;
        this.isDeleted = false;
        this.boardState = BoardState.ACTIVE;
    }

    public static Board createBoard(Board board, User user) {
        return Board.builder()
                .content(board.content)
                .user(user)
                .build();
    }


    public void addBoardImage(BoardImage boardImage) {
        boardImages.add(boardImage);
        boardImage.setBoard(this);
    }

    public void addLike(Like like) {
        likeList.add(like);
        like.setBoard(this);
    }

    public void deleteBoard() {
        this.isDeleted = true;
        this.boardState = BoardState.DELETE;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
