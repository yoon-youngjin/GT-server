package dev.yoon.gridgetest.domain.message.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SendMessageReq {

    @NotNull(message = "보낼 유저의 아이디는 필수값 입니다.")
    private Long toUserId;

    @Size(min = 1, max = 200, message = "200자 이내로 작성해주세요.")
    @NotBlank(message = "메시지 내용은 필수값 입니다.")

    private String content;

}
