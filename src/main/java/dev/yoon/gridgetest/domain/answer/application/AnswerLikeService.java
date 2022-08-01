package dev.yoon.gridgetest.domain.answer.application;

import dev.yoon.gridgetest.domain.answer.domain.Answer;
import dev.yoon.gridgetest.domain.answer.domain.AnswerLike;
import dev.yoon.gridgetest.domain.answer.repository.AnswerLikeRepository;
import dev.yoon.gridgetest.domain.answer.repository.AnswerRepository;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.domain.Like;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AnswerLikeService {

    private final AnswerLikeRepository answerLikeRepository;

    public Boolean existsUser(Answer answer, User user) {
        return answerLikeRepository.existsByAnswerAndUser(answer, user);

    }


    public void deleteLike(Answer answer, User user) {
        AnswerLike answerLike = answerLikeRepository.findByAnswerAndUser(answer, user)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.LIKE_NOT_FOUND));

        answerLikeRepository.delete(answerLike);

    }

    public void addLike(AnswerLike answerLike) {
        answerLikeRepository.save(answerLike);
    }

}
