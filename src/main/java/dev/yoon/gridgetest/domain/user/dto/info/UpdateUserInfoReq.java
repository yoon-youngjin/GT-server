package dev.yoon.gridgetest.domain.user.dto.info;

import dev.yoon.gridgetest.domain.user.domain.User;
import dev.yoon.gridgetest.domain.user.model.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserInfoReq {

    private MultipartFile profileImg;

    @NotBlank(message = "이름은 필수값 입니다.")
    @Pattern(regexp = "[a-z0-9_.]{1,20}", message = "이름은 영문, 숫자, 특수문자('_', '.')을 포함하여 20자 이내로 가능합니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수값 입니다.")
    @Pattern(regexp = "[a-z0-9_.]{1,20}", message = "이름은 영문, 숫자, 특수문자('_', '.')을 포함하여 20자 이내로 가능합니다.")
    private String nickname;

    private String webSite;

    private String description;

    public User toEntity(String profileImgUrl) {

        User user = User.builder()
                .nickname(Nickname.from(nickname))
                .name(Name.from(name))
                .webSite(webSite)
                .description(description)
                .profileUrl(profileImgUrl)
                .build();

        return user;

    }


}
