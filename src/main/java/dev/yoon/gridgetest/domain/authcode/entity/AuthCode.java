package dev.yoon.gridgetest.domain.authcode.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash("authCode")
@Getter
@AllArgsConstructor
@Builder
public class AuthCode {

    @Id
    private String id;

    private String code;

//    private Date expiration;

    public static AuthCode of(String phone, String code) {
        return AuthCode.builder()
                .id(phone)
                .code(code)
                .build();
    }

}
