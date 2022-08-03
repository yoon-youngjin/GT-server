package dev.yoon.gridgetest.domain.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.yoon.gridgetest.domain.admin.dto.GetUserInfoDto;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.UserState;
import dev.yoon.gridgetest.global.util.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static dev.yoon.gridgetest.domain.user.domain.QUser.user;


@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<User> findAllUsersByQuery(Pageable pageable, GetUserInfoDto.Request query) {


        List<User> results = queryFactory
                .selectFrom(user)
                .where(
                        searchLikeName(query.getName()),
                        searchLikeNickname(query.getNickname()),
                        searchByBirth(query.getBirth()),
                        searchByState(query.getUserState())
                )
                .orderBy(user.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(user)
                .where(
                        searchLikeName(query.getName()),
                        searchLikeNickname(query.getNickname()),
                        searchByBirth(query.getBirth()),
                        searchByState(query.getUserState())
                )
                .fetch().size();

        return new PageImpl<>(results, pageable, totalSize);
    }

    private BooleanExpression searchLikeName(String name) {
        return name == null ? null : user.name.value.like("%" + name + "%");
    }

    private BooleanExpression searchLikeNickname(String nickname) {
        return nickname == null ? null : user.nickname.value.like("%" + nickname + "%");
    }

    private BooleanExpression searchByBirth(String birth) {
        return birth == null ? null : user.birth.eq(DateTimeUtils.convertToLocalDate(birth));
    }


    private BooleanExpression searchByState(String state) {
        return state == null ? null : user.userState.eq(UserState.valueOf(state));

    }


}
