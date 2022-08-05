package dev.yoon.gridgetest.domain.follow.repository;

import dev.yoon.gridgetest.domain.follow.domain.Follow;
import dev.yoon.gridgetest.domain.follow.model.FollowState;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByToAndFollowState(User to, FollowState followState);


    @Query("select f from Follow f " +
            "where f.Id=:followId and f.to=:to and f.followState=:followState ")
    Optional<Follow> findByIdAndToAndFollowState(Long followId, User to, FollowState followState);

    Optional<Follow> findByFromAndToAndFollowState(User from, User to, FollowState followState);

    Long countAllByTo(User to);

    Long countAllByFrom(User from);

    Optional<Follow> findByFromAndTo(User from, User to);
}
