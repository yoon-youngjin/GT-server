package dev.yoon.gridgetest.domain.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "BOARD_IMAGE")
@Getter
@NoArgsConstructor
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_IMAGE_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    @Column(name = "BOARD_IMAGE_NAME", nullable = false)
    private String imageName;

    @Column(name = "BOARD_IMAGE_URL", nullable = false)
    private String imageUrl;

    @Builder
    public BoardImage(Board board, String imageName, String imageUrl) {
        this.board = board;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public static BoardImage createBoardImage(Board board, String imageName, String imageUrl) {
        return BoardImage.builder()
                .board(board)
                .imageName(imageName)
                .imageUrl(imageUrl)
                .build();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

}
