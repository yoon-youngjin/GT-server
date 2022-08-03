package dev.yoon.gridgetest.domain.follow.dto;

import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.dto.GetMainBoardRes;
import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GetRequestFollowRes {

    private Long userId;

    private String profileUrl;

    private String nickname;

    @Builder
    public GetRequestFollowRes(Long userId, String profileUrl, String nickname) {
        this.userId = userId;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }

    public static GetRequestFollowRes from(User user) {

        return GetRequestFollowRes.builder()
                .userId(user.getId())
                .nickname(user.getNickname().getValue())
                .profileUrl(user.getProfileUrl())
                .build();
    }
}
