package dev.yoon.gridgetest.domain.user.dto.signup;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.domain.jwt.dto.TokenDto;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.Birth;
import dev.yoon.gridgetest.domain.user.model.Password;
import dev.yoon.gridgetest.domain.user.model.UserType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

public class SignUpDto {

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Request {

        @NotBlank(message = "전화번호는 필수값 입니다.")
        @Length(min = 1, max = 11, message = "최대 11자리까지 입력할 수 있습니다")
        private String phone;

        @NotBlank(message = "이름은 필수값 입니다.")
        @Length(min = 1, max = 20, message = "최대 20자리까지 입력할 수 있습니다")
        private String name;

        @NotBlank(message = "비밀번호는 필수값 입니다.")
        @Length(min = 1, max = 20, message = "최대 20자리까지 입력할 수 있습니다")
        private String password;

        @NotBlank(message = "생년월일을 필수값 입니다.")
        private String birth;

        @NotBlank(message = "닉네임은 필수값 입니다.")
        @Length(min = 1, max = 20, message = "최대 20자리까지 입력할 수 있습니다")
        private String nickname;


        public User toEntity() {

            User user = User.builder()
                    .email(null)
                    .password(Password.toPassword(password))
                    .userType(UserType.GENERAL)
                    .nickname(nickname)
                    .phoneNumber(phone)
                    .name(name)
                    .birth(Birth.toBirth(birth))
                    .build();

            return user;

        }


    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private String grantType;

        private String accessToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date accessTokenExpireTime;

        private String refreshToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private Date refreshTokenExpireTime;

        public static Response from(TokenDto tokenDto) {
            return Response.builder()
                    .grantType(tokenDto.getGrantType())
                    .accessToken(tokenDto.getAccessToken())
                    .accessTokenExpireTime(tokenDto.getAccessTokenExpireTime())
                    .refreshToken(tokenDto.getRefreshToken())
                    .refreshTokenExpireTime(tokenDto.getRefreshTokenExpireTime())
                    .build();
        }


    }

}
