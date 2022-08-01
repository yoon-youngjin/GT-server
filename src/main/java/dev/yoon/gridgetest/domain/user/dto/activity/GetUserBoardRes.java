package dev.yoon.gridgetest.domain.user.dto.activity;

import dev.yoon.gridgetest.domain.board.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetUserBoardRes {

    private Long boardId;

    private String boardImgUrl;

    @Builder
    public GetUserBoardRes(Long boardId, String boardImgUrl) {
        this.boardId = boardId;
        this.boardImgUrl = boardImgUrl;
    }

    public static GetUserBoardRes from(Board board) {

        GetUserBoardRes res = GetUserBoardRes.builder()
                .boardId(board.getId())
                .build();

        if (board.getBoardImages().size() != 0) {
            res.setBoardImgUrl(board.getBoardImages().get(0).getImageUrl());
        }
        return res;


    }



}
