package dev.yoon.gridgetest.domain.user.dto.info;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordReq {


    @NotBlank(message = "비밀번호는 필수값 입니다.")
    @Length(min = 1, max = 20, message = "최대 20자리까지 입력할 수 있습니다")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수값 입니다.")
    @Length(min = 1, max = 20, message = "최대 20자리까지 입력할 수 있습니다")
    private String checkPassword;

}
