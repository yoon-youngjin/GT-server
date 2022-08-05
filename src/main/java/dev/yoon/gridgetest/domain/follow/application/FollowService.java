package dev.yoon.gridgetest.domain.follow.application;

import dev.yoon.gridgetest.domain.follow.domain.Follow;
import dev.yoon.gridgetest.domain.follow.dto.FollowUserReq;
import dev.yoon.gridgetest.domain.follow.dto.GetRequestFollowRes;
import dev.yoon.gridgetest.domain.follow.model.FollowState;
import dev.yoon.gridgetest.domain.follow.repository.FollowRepository;
import dev.yoon.gridgetest.domain.user.application.UserService;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

        Optional<Follow> optionalFollow = getFollowByFromAndTo(from, to);
        // 팔로우 존재
        if (optionalFollow.isPresent()) {

            Follow follow = optionalFollow.get();
            if (follow.getFollowState() == FollowState.ACCEPT) {
                throw new BusinessException(ErrorCode.EXISTS_FOLLOW);
            }else {
                followRepository.delete(follow);
            }

        }else {
            FollowState followState;
            if (to.getIsPrivateUser()) {
                followState = FollowState.WAITING;
            } else {
                followState = FollowState.ACCEPT;
            }

            Follow follow = Follow.createFollow(from, to, followState);
            followRepository.save(follow);
        }
    }

    public List<GetRequestFollowRes> getRequestFollow(String phone) {

        User to = userService.getUserByPhoneNumber(phone);
        List<Follow> follows = followRepository.findAllByToAndFollowState(to, FollowState.WAITING);

        return follows.stream().map(GetRequestFollowRes::from).collect(Collectors.toList());
    }

    @Transactional
    public void acceptFollow(Long followId, String phone) {
        User to = userService.getUserByPhoneNumber(phone);
        Follow follow = getFollowMeById(followId, to);

        follow.updateState();

    }

    @Transactional
    public void rejectFollow(Long followId, String phone) {
        User to = userService.getUserByPhoneNumber(phone);
        Follow follow = getFollowMeById(followId, to);

        followRepository.delete(follow);

    }


    public Follow getFollowMeById(Long followId, User to) {
        return followRepository.findByIdAndToAndFollowState(followId, to, FollowState.WAITING)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FOLLOW_NOT_FOUND));
    }

    public Optional<Follow> getFollowByFromAndTo(User from, User to) {
        return followRepository.findByFromAndTo(from, to);
    }

    public Long getFollowerCount(User user) {
        return followRepository.countAllByTo(user);
    }

    public Long getFollowingCount(User user) {
        return followRepository.countAllByFrom(user);
    }
}
