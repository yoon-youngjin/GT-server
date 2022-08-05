package dev.yoon.gridgetest.domain.user.dto.signup;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class SendSmsReq {

    @Size(min = 1, max = 11, message = "최대 11자리까지 입력할 수 있습니다.")
    @NotBlank(message = "전화번호는 필수값 입니다.")
    private String phone;


}
