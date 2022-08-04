package dev.yoon.gridgetest.domain.admin.dto.users;

import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.UserState;
import dev.yoon.gridgetest.global.validator.Enum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.time.format.DateTimeFormatter;

public class GetUserInfoDto {

    @Getter
    @Setter
    public static class Request {

        private String name;

        private String nickname;

        @Pattern(regexp = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])", message = "YYYYMMDD 형식으로 입력해주세요.")
        private String Birth; // YYYYMMDD

        @Enum(enumClass = UserState.class, message ="잘못된 Enum 값 입니다.", ignoreCase = true)
        private String userState; // ACTIVE, INACTIVE, PAUSE

    }

    @Getter
    @Setter
    public static class Response {


        private Long userId;

        private String nickname;

        private String phone;

        private String birth;

        @Builder
        public Response(Long userId,String nickname, String phone, String birth) {
            this.userId = userId;
            this.nickname = nickname;
            this.phone = phone;
            this.birth = birth;
        }

        public static Response from(User user) {

            Response response = Response.builder()
                    .userId(user.getId())
                    .nickname(user.getNickname().getValue())
                    .phone(user.getPhoneNumber())
                    .birth(user.getBirth().format(DateTimeFormatter.ofPattern("yy.MM.dd")))
                    .build();

            return response;

        }
    }
}
