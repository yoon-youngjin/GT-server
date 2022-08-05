package dev.yoon.gridgetest.domain.user.dto.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.global.jwt.dto.TokenDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class OauthSignUpDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "소셜 타입은 필수값 입니다.")
        private String userType;

        @NotBlank(message = "전화번호는 필수값 입니다.")
        @Length(min = 1, max = 11, message = "최대 11자리까지 입력할 수 있습니다")
        private String phone;

        @NotBlank(message = "이름은 필수값 입니다.")
        @Pattern(regexp = "[a-z0-9_.]{1,20}", message = "이름은 영문, 숫자, 특수문자('_', '.')을 포함하여 20자 이내로 가능합니다.")
        private String name;

        @NotBlank(message = "생년월일을 필수값 입니다.")
        @Pattern(regexp = "(19|20)\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])", message = "YYYYMMDD 형식으로 입력해주세요.")
        private String birth;

        @NotBlank(message = "닉네임은 필수값 입니다.")
        @Pattern(regexp = "[a-z0-9_.]{1,20}", message = "이름은 영문, 숫자, 특수문자('_', '.')을 포함하여 20자 이내로 가능합니다.")
        private String nickname;

    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String grantType;

        private String accessToken;

        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Date accessTokenExpireTime;

        private String refreshToken;

        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
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
