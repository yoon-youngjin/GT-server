package dev.yoon.gridgetest.domain.answer.repository;

import dev.yoon.gridgetest.domain.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerRepositoryCustom{
}
