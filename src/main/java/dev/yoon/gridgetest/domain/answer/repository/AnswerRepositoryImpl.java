package dev.yoon.gridgetest.domain.answer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yoon.gridgetest.domain.answer.domain.Answer;
import dev.yoon.gridgetest.domain.answer.dto.GetAnswerRes;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static dev.yoon.gridgetest.domain.answer.domain.QAnswer.answer;
import static dev.yoon.gridgetest.domain.user.domain.QUser.user;


@Repository
public class AnswerRepositoryImpl implements AnswerRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public AnswerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Slice<Answer> findAnswersByBoard(Pageable pageable, User me, Board board) {

        List<Answer> results = queryFactory
                .selectFrom(answer)
                .join(answer.user, user).fetchJoin()
                .where(answer.board.eq(board))
                .orderBy(answer.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;
        if (results.size() > pageable.getPageSize()) {
            results.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(results, pageable, hasNext);

    }

}
