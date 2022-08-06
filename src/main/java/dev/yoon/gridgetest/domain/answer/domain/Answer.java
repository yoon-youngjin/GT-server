package dev.yoon.gridgetest.domain.answer.domain;

import dev.yoon.gridgetest.domain.base.BaseTimeEntity;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.domain.Like;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ANSWER")
@Getter
@NoArgsConstructor
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_ID")
    private Long Id;

    @Lob
    @Column(length = 200, nullable = false)
    private String comment;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Answer parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Answer> replies = new ArrayList<>();

    @OneToMany(
            mappedBy = "answer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<AnswerLike> likeList = new ArrayList<>();



    @Builder
    public Answer(String comment, User user, Board board) {
        this.comment = comment;
        this.user = user;
        this.board = board;
    }

    public static Answer createAnswer(User user, Board board, Answer answer) {
        return Answer.builder()
                .comment(answer.comment)
                .user(user)
                .board(board)
                .build();
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public void updateAnswer(Answer updateAnswer) {
        this.comment = updateAnswer.comment;
    }

    public void addLike(AnswerLike answerLike) {
        likeList.add(answerLike);
        answerLike.setAnswer(this);
    }

    public void setParent(Answer answer) {
        this.parent = answer;
    }


}
