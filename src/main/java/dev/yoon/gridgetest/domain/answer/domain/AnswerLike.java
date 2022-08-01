package dev.yoon.gridgetest.domain.answer.domain;

import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "answer_like")
@Getter
@NoArgsConstructor
public class AnswerLike {

    @javax.persistence.Id
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
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @Builder
    public AnswerLike(Answer answer, User user) {
        this.answer = answer;
        this.user = user;
    }

    public static AnswerLike createLike(Answer answer, User user) {
        return AnswerLike.builder()
                .answer(answer)
                .user(user)
                .build();
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

}
