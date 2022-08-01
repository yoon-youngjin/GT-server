package dev.yoon.gridgetest.domain.board.domain;

import dev.yoon.gridgetest.domain.answer.domain.Answer;
import dev.yoon.gridgetest.domain.base.BaseTimeEntity;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Lob
    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "board",
            cascade = CascadeType.ALL
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

    @Builder
    public Board(String content, User user) {
        this.content = content;
        this.user = user;
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
}
