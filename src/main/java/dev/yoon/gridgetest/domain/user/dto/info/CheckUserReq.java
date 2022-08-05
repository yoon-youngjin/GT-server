package dev.yoon.gridgetest.domain.user.dto.info;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckUserReq {

    @NotBlank(message = "전화번호는 필수값 입니다.")
    @Length(min = 1, max = 11, message = "최대 11자리까지 입력할 수 있습니다")
    private String phone;

}
