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

//    @Override
//    public Slice<CommunitySimpleDto.Response> findPageCommunityByMBTI(Pageable pageable, String mbti, User curUser) {
//
//        List<Community> results = queryFactory
//                .selectFrom(community)
//                .join(community.user, user).fetchJoin()
//                .where(
//                       findByMBTI(mbti),
//                        community.communityId.notIn(getReportsByUser(curUser)),
//                        community.user.notIn(getBlockUser(curUser))
//                )
//                .groupBy(community.communityId)
//                .orderBy(listSort(pageable))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize() + 1)
//                .fetch();
//
//        List<CommunitySimpleDto.Response> contents = results.stream().map(community ->
//                CommunitySimpleDto.Response.of(community, curUser)
//        ).collect(Collectors.toList());
//
//
//        boolean hasNext = false;
//        if (contents.size() > pageable.getPageSize()) {
//            contents.remove(pageable.getPageSize());
//            hasNext = true;
//        }
//
//        return new SliceImpl<>(contents, pageable, hasNext);
//
//
//    }
//
//    @Override
//    public Slice<CommunitySearchDto.Response> findSearchCommunity(Pageable pageable, String search, User curUser) {
//
//
//        List<Community> results = queryFactory
//                .selectFrom(community)
//                .join(community.user, user).fetchJoin()
//                .where(
//                        searchByLike(search),
//                        community.communityId.notIn(getReportsByUser(curUser)),
//                        community.user.notIn(getBlockUser(curUser))
//                )
//                .groupBy(community.communityId)
//                .orderBy(listSort(pageable))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize() + 1)
//                .fetch();
//
//        List<CommunitySearchDto.Response> contents = results.stream().map(community ->
//                CommunitySearchDto.Response.of(community, curUser)
//        ).collect(Collectors.toList());
//
//        boolean hasNext = false;
//        if (contents.size() > pageable.getPageSize()) {
//            contents.remove(pageable.getPageSize());
//            hasNext = true;
//        }
//
//        return new SliceImpl<>(contents, pageable, hasNext);
//
//    }
//
//    private BooleanExpression searchByLike(String searchQuery) {
//
//        return community.title.like("%" + searchQuery + "%").or(community.content.like("%" + searchQuery + "%"));
//
//    }
//
//    private BooleanExpression findByMBTI(String mbti) {
//
//        return mbti == null ? null : community.mbti.eq(MBTI.from(mbti));
//
//    }
//
//    private OrderSpecifier<?> listSort(Pageable pageable) {
//
//        if (!pageable.getSort().isEmpty()) {
//            for (Sort.Order order : pageable.getSort()) {
//                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
//                switch (order.getProperty()) {
//                    case "ANSWER_COUNT":
//                        return new OrderSpecifier<>(direction, community.communityAnswers.size());
//                    case "CREATE_TIME":
//                        return new OrderSpecifier<>(direction, community.createTime);
//                }
//            }
//        }
//        return null;
//    }
//
//    private List<Long> getReportsByUser(User user) {
//
//        return queryFactory
//                .selectFrom(report)
//                .where(report.serviceType.eq(ServiceType.COMMUNITY), report.from.userId.eq(user.getUserId()))
//                .fetch().stream().map(Report::getServiceId).collect(Collectors.toList());
//
//    }
//
//    private List<User> getBlockUser(User user) {
//
//        return queryFactory
//                .selectFrom(block)
//                .where(block.from.eq(user))
//                .fetch().stream().map(Block::getTo).collect(Collectors.toList());
//
//    }


}
