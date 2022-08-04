package dev.yoon.gridgetest.domain.follow.repository;

import dev.yoon.gridgetest.domain.follow.domain.Follow;
import dev.yoon.gridgetest.domain.follow.model.FollowState;
import dev.yoon.gridgetest.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByToAndFollowState(User to, FollowState followState);

    Optional<Follow> findByFromAndToAndFollowState(User from, User to, FollowState followState);

    Long countAllByTo(User to);

    Long countAllByFrom(User from);

}
