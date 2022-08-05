package dev.yoon.gridgetest.domain.report.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReportBoardReq {

    @NotNull(message = "글 아이디는 필수값 입니다.")
    private Long boardId;

    @NotBlank(message = "신고하는 이유는 필수값 입니다.")
    private String reason;

}
