package dev.yoon.gridgetest.domain.user.dto.login;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.global.jwt.dto.TokenDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;


public class LoginDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        private String id;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        private String password;


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
