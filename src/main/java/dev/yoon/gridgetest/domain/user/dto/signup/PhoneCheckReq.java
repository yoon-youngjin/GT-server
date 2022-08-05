package dev.yoon.gridgetest.domain.user.dto.signup;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class PhoneCheckReq {

    // 정규식 검사
    @NotBlank
    @Size(min = 1, max = 11, message = "핸드폰 번호는 11자 이내로 적어주세요.")
    private String phone;

}
