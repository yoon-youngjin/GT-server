package dev.yoon.gridgetest.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserState {

    ACTIVE("활성화"),
    INACTIVE("탈퇴"),
    PAUSE("정지");

    private final String value;

}
