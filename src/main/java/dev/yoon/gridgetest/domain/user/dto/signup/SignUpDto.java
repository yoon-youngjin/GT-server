package dev.yoon.gridgetest.domain.user.dto.signup;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.domain.jwt.dto.TokenDto;
import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.*;
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
        @Pattern(regexp = "[a-z0-9_.]{1,20}", message = "이름은 영문, 숫자, 특수문자('_', '.')을 포함하여 20자 이내로 가능합니다.")
        private String name;

        @NotBlank(message = "닉네임은 필수값 입니다.")
        @Pattern(regexp = "[a-z0-9_.]{1,20}", message = "이름은 영문, 숫자, 특수문자('_', '.')을 포함하여 20자 이내로 가능합니다.")
        private String nickname;

        @NotBlank(message = "비밀번호는 필수값 입니다.")
        @Length(min = 1, max = 20, message = "최대 20자리까지 입력할 수 있습니다")
        private String password;

        @NotBlank(message = "생년월일을 필수값 입니다.")
        private String birth;

        public User toEntity() {

            User user = User.builder()
                    .email(null)
                    .password(Password.from(password))
                    .userType(UserType.GENERAL)
                    .nickname(Nickname.from(nickname))
                    .phoneNumber(phone)
                    .name(Name.from(name))
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
