package dev.yoon.gridgetest.domain.admin.dto.board;


import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.model.BoardState;
import dev.yoon.gridgetest.domain.user.model.Role;
import dev.yoon.gridgetest.domain.user.model.UserState;
import dev.yoon.gridgetest.domain.user.model.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetBoardDetailRes {

    private Long boardId;

    private Long userId;

    private String content;

    private BoardState boardState;

    private Boolean isDeleted;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime updatedTime;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime createdTime;

    @Builder
    public GetBoardDetailRes(Long boardId, Long userId, Boolean isDeleted, String content, BoardState boardState, LocalDateTime updatedTime, LocalDateTime createdTime) {
        this.boardId = boardId;
        this.userId = userId;
        this.isDeleted = isDeleted;
        this.content = content;
        this.boardState = boardState;
        this.updatedTime = updatedTime;
        this.createdTime = createdTime;
    }


    public static GetBoardDetailRes from(Board board) {

        return GetBoardDetailRes.builder()
                .boardId(board.getId())
                .userId(board.getUser().getId())
                .isDeleted(board.getIsDeleted())
                .content(board.getContent())
                .boardState(board.getBoardState())
                .updatedTime(board.getUpdateTime())
                .createdTime(board.getCreateTime())
                .build();
    }
}
