package dev.yoon.gridgetest.domain.answer.dto;

import dev.yoon.gridgetest.domain.answer.domain.Answer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateAnswerReq {

    @Size(max = 200, message = "200자 이하로 작성해주세요.")
    @NotBlank(message = "글 내용은 필수값 입니다.")
    private String comment;

    public Answer toEntity() {
        return Answer.builder()
                .comment(comment)
                .build();

    }


}
