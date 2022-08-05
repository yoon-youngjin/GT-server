package dev.yoon.gridgetest.domain.user.dto.signup;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class NicknameCheckReq {

    @NotBlank
    @Size(min = 1, max = 20, message = "닉네임은 20자 이내로 적어주세요.")
    private String nickname;

}
