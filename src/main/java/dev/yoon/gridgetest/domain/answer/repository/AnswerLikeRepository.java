package dev.yoon.gridgetest.domain.answer.repository;

import dev.yoon.gridgetest.domain.answer.domain.Answer;
import dev.yoon.gridgetest.domain.answer.domain.AnswerLike;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerLikeRepository extends JpaRepository<AnswerLike, Long>{

    Optional<AnswerLike> findByAnswerAndUser(Answer answer, User user);

    Boolean existsByAnswerAndUser(Answer answer, User user);
}
