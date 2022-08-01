package dev.yoon.gridgetest.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class UpdateBoardReq {

    @NotBlank(message = "글 내용은 필수값 입니다.")
    private String content;

    @NotNull(message = "글 아이디는 필수값 입니다.")
    private Long boardId;



}
