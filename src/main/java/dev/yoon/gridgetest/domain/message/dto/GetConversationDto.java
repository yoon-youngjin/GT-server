package dev.yoon.gridgetest.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.domain.message.domain.Message;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class GetConversationDto {

    @Getter
    @Setter
    public static class Request {

        @NotNull(message = "보낸 유저의 아이디는 필수값 입니다.")
        private Long userId;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long fromUserId;

        private Long toUserId;

        private String content;

        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private LocalDateTime createTime;

        public static Response from(Message message) {

            Response response = Response.builder()
                    .fromUserId(message.getFrom().getId())
                    .toUserId(message.getTo().getId())
                    .content(message.getContent())
                    .createTime(message.getCreateTime())
                    .build();

            return response;

        }
    }

}
