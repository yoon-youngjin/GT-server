package dev.yoon.gridgetest.domain.user.dto.signup;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Getter @Setter
public class SendSmsReq {

    @Max(value = 11, message = "최대 11자리까지 입력할 수 있습니다.")
    @NotBlank(message = "전화번호는 필수값 입니다.")
    private String phone;


}
