package dev.yoon.gridgetest.domain.follow.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FollowUserReq {

    @NotNull(message = "유저 아이디는 필수값 입니다.")
    private Long userId;
}
