package dev.yoon.gridgetest.domain.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "board_image")
@Getter
@NoArgsConstructor
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name = "board_image_filename", nullable = false)
    private String imageName;

    @Column(name = "board_image_url", nullable = false)
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
