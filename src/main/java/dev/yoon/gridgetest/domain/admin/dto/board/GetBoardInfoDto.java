package dev.yoon.gridgetest.domain.admin.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.domain.board.domain.Board;
import dev.yoon.gridgetest.domain.board.model.BoardState;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.UserState;
import dev.yoon.gridgetest.global.validator.Enum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GetBoardInfoDto {

    @Getter
    @Setter
    public static class Request {

        private String nickname;

        @Pattern(regexp = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])", message = "YYYYMMDD 형식으로 입력해주세요.")
        private String createTime; // YYYYMMDD

        @Enum(enumClass = BoardState.class, message = "잘못된 Enum 값 입니다.", ignoreCase = true)
        private String boardState; // ACTIVE, DELETE, DELETE_PROCESSING

    }

    @Getter
    @Setter
    public static class Response {

        private Long boardId;

        private Long userId;

        private String nickname;

        private String phone;

        private String content;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd", timezone = "Asia/Seoul")
        private LocalDateTime creatTime;

        @Builder
        public Response(Long boardId, Long userId, String nickname, String phone, String content, LocalDateTime creatTime) {
            this.boardId = boardId;
            this.userId = userId;
            this.nickname = nickname;
            this.phone = phone;
            this.content = content;
            this.creatTime = creatTime;
        }


        public static Response of(Board board, User user) {

            Response response = Response.builder()
                    .boardId(board.getId())
                    .userId(user.getId())
                    .nickname(user.getNickname().getValue())
                    .phone(user.getPhoneNumber())
                    .content(board.getContent())
                    .creatTime(board.getCreateTime())
                    .build();

            return response;

        }
    }
}
