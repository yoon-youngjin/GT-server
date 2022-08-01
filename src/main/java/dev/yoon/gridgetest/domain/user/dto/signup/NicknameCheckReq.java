package dev.yoon.gridgetest.domain.user.dto.signup;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class NicknameCheckReq {

    @NotBlank
    @Size(min = 1, max = 6, message = "닉네임은 6자 이내로 적어주세요.")
    private String nickname;

}
