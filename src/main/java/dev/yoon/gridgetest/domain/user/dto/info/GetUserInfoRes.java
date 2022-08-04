package dev.yoon.gridgetest.domain.user.dto.info;

import dev.yoon.gridgetest.domain.user.domain.User;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoRes {

    private Long userId;

    private String nickname;

    private String name;

    private String profileUrl;

    private Long followerCnt;

    private Long followingCnt;

    private Long boardCnt;

    public static GetUserInfoRes of(User user, Long boardCnt, Long followerCnt, Long followingCnt) {

        return GetUserInfoRes.builder()
                .userId(user.getId())
                .name(user.getName().getValue())
                .nickname(user.getNickname().getValue())
                .followerCnt(followerCnt)
                .followingCnt(followingCnt)
                .boardCnt(boardCnt)
                .build();

    }
}
