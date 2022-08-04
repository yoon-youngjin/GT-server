package dev.yoon.gridgetest.domain.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardState {

    ACTIVE("활성화"),
    DELETE("본인 삭제"),
    DELETE_PROCESSING("삭제 처리");

    private final String value;

}
