package dev.yoon.gridgetest.domain.report.dto;

import dev.yoon.gridgetest.domain.report.model.ServiceType;
import dev.yoon.gridgetest.global.validator.Enum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReportServiceReq {

    @NotBlank(message = "서비스 타입은 필수값 입니다.")
    @Enum(enumClass = ServiceType.class, message ="잘못된 Enum 값 입니다.")
    private String serviceType;

    @NotBlank(message = "신고하는 이유는 필수값 입니다.")
    private String reason;

    @NotNull(message = "신고할 서비스의 아이디는 필수값 입니다.")
    private Long serviceId;


}
