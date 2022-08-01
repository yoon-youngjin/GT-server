package dev.yoon.gridgetest.domain.jwt.constant;

import lombok.Getter;

@Getter
public enum GrantType {

    BEARRER("Bearer");

    GrantType(String type) {
        this.type = type;
    }

    private String type;

}
