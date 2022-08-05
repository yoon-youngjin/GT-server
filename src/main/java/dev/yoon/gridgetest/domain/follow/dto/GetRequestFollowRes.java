package dev.yoon.gridgetest.domain.follow.dto;

import dev.yoon.gridgetest.domain.follow.domain.Follow;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetRequestFollowRes {

    private Long followId;

    private Long userId;

    private String profileUrl;

    private String nickname;

    @Builder
    public GetRequestFollowRes(Long followId, Long userId, String profileUrl, String nickname) {
        this.followId = followId;
        this.userId = userId;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }

    public static GetRequestFollowRes from(Follow follow) {

        return GetRequestFollowRes.builder()
                .followId(follow.getId())
                .userId(follow.getFrom().getId())
                .nickname(follow.getTo().getNickname().getValue())
                .profileUrl(follow.getFrom().getProfileUrl())
                .build();
    }
}
