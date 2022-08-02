package dev.yoon.gridgetest.domain.user.dto.info;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckUpdateNicknameReq {

    @NotBlank(message = "닉네임은 필수값 입니다.")
    @Pattern(regexp = "[a-z0-9_.]{1,20}", message = "이름은 영문, 숫자, 특수문자('_', '.')을 포함하여 20자 이내로 가능합니다.")
    private String nickname;

}
