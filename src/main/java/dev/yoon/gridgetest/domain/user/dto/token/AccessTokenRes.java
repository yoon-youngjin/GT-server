package dev.yoon.gridgetest.domain.user.dto.token;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.yoon.gridgetest.global.jwt.constant.GrantType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class AccessTokenRes {

    private String grantType;

    private String accessToken;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date accessTokenExpireTime;

    public static AccessTokenRes of(String accessToken, Date accessTokenExpireTime) {

        return AccessTokenRes.builder()
                .grantType(GrantType.BEARRER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }

}
