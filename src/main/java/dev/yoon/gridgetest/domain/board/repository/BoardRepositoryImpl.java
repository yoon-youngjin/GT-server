package dev.yoon.gridgetest.domain.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.dto.GetMainBoardRes;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static dev.yoon.gridgetest.domain.answer.domain.QAnswer.answer;
import static dev.yoon.gridgetest.domain.board.domain.QBoard.board;
import static dev.yoon.gridgetest.domain.user.domain.QUser.user;


@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<GetMainBoardRes> findMainBoard(Pageable pageable, User me) {

        List<Board> results = queryFactory
                .selectFrom(board)
                .join(board.user, user).fetchJoin()
                .orderBy(board.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        List<GetMainBoardRes> contents = results.stream().map(board ->
                GetMainBoardRes.of(board, me)
        ).collect(Collectors.toList());

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);
    }

}
