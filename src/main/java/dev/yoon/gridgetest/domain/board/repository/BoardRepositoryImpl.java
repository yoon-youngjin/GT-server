package dev.yoon.gridgetest.domain.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yoon.gridgetest.domain.admin.dto.board.GetBoardInfoDto;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.dto.GetMainBoardRes;
import dev.yoon.gridgetest.domain.board.model.BoardState;
import dev.yoon.gridgetest.domain.user.domain.QUser;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.util.DateTimeUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

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
                .where(board.isDeleted.isFalse())
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

    @Override
    public Page<Board> findAllBoardByQuery(Pageable pageable, GetBoardInfoDto.Request query) {

        List<Board> results = queryFactory
                .selectFrom(board)
                .join(board.user, user).fetchJoin()
                .where(
                        searchLikeNickname(board.user, query.getNickname()),
                        searchByCreateTime(query.getCreateTime()),
                        searchByState(query.getBoardState())
                )
                .orderBy(board.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(board)
                .join(board.user, user).fetchJoin()
                .where(
                        searchLikeNickname(board.user, query.getNickname()),
                        searchByCreateTime(query.getCreateTime()),
                        searchByState(query.getBoardState())
                )
                .fetch().size();

        return new PageImpl<>(results, pageable, totalSize);
    }


    private BooleanExpression searchLikeNickname(QUser user, String nickname) {
        return nickname == null ? null : user.nickname.value.like("%" + nickname + "%");
    }

    private BooleanExpression searchByCreateTime(String createTime) {

        return createTime == null ?
                null :
                board.createTime.between(DateTimeUtils.convertToLocalDate(createTime).atStartOfDay(),
                        DateTimeUtils.convertToLocalDate(createTime).atTime(23, 59, 59));
    }

    private BooleanExpression searchByState(String state) {
        return state == null ? null : board.boardState.eq(BoardState.valueOf(state));

    }


}
