package dev.yoon.gridgetest.domain.follow.application;

import dev.yoon.gridgetest.domain.follow.domain.Follow;
import dev.yoon.gridgetest.domain.follow.dto.FollowUserReq;
import dev.yoon.gridgetest.domain.follow.dto.GetRequestFollowRes;
import dev.yoon.gridgetest.domain.follow.model.FollowState;
import dev.yoon.gridgetest.domain.follow.repository.FollowRepository;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FollowService {

    private final FollowRepository followRepository;
    private final UserService userService;

    @Transactional
    public void follow(FollowUserReq request, String phone) {

        User from = userService.getUserByPhoneNumber(phone);
        User to = userService.getUserById(request.getUserId());
        FollowState followState;

        if (to.getIsPrivateUser()) {
            followState = FollowState.WAITING;
        } else {
            followState = FollowState.ACCEPT;
        }

        Follow follow = Follow.createFollow(from, to, followState);
        followRepository.save(follow);

    }

    public List<GetRequestFollowRes> getRequestFollow(String phone) {

        User to = userService.getUserByPhoneNumber(phone);
        List<Follow> follows = followRepository.findAllByToAndFollowState(to, FollowState.WAITING);

        return follows.stream().map(follow -> GetRequestFollowRes.from(follow.getFrom())).collect(Collectors.toList());
    }

    @Transactional
    public void acceptFollow(Long followId, String phone) {
        User me = userService.getUserByPhoneNumber(phone);
        User from = userService.getUserById(followId);
        Follow follow = getFollowByWaiting(from, me);

        follow.updateState();

    }

    @Transactional
    public void rejectFollow(Long followId, String phone) {
        User me = userService.getUserByPhoneNumber(phone);
        User from = userService.getUserById(followId);
        Follow follow = getFollowByWaiting(from, me);

        followRepository.delete(follow);

    }

    @Transactional
    public void cancelFollow(Long followerId, String phone) {
        User from = userService.getUserByPhoneNumber(phone);
        User to = userService.getUserById(followerId);
        Follow follow = getFollowByWaiting(from, to);

        followRepository.delete(follow);

    }

    public Follow getFollowByWaiting(User from, User to) {
        return followRepository.findByFromAndToAndFollowState(from, to, FollowState.WAITING)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FOLLOW_NOT_FOUND));
    }

    public Long getFollowerCount(User user) {
        return followRepository.countAllByTo(user);
    }

    public Long getFollowingCount(User user) {
        return followRepository.countAllByFrom(user);
    }
}
