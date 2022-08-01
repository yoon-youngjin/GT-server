package dev.yoon.gridgetest.domain.user.dto.login;

import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.Birth;
import dev.yoon.gridgetest.domain.user.model.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Getter
@ToString
@Builder
public class OAuthAttributes {

    private String socialId;
    private String email;
    private UserType userType;

    public User toEntity(OauthSignUpDto.Request request) {

        User user = User.builder()
                .email(null)
                .phoneNumber(request.getPhone())
                .name(request.getName())
                .nickname(request.getNickname())
                .password(null)
                .birth(Birth.toBirth(request.getBirth()))
                .userType(userType)
                .socialId(socialId)
                .build();

        return user;
    }

}

