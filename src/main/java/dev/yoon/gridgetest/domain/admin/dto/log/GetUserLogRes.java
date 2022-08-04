package dev.yoon.gridgetest.domain.admin.dto.log;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetUserLogRes {


    private String content;

    private String nickname;

    private String loggingTime;

    @Builder
    public GetUserLogRes(String content, String nickname, String loggingTime) {

        this.content = content;
        this.nickname = nickname;
        this.loggingTime = loggingTime;
    }


    public static GetUserLogRes from(String log) {
        String[] logs = log.split("/");

        return GetUserLogRes.builder()
                .content(logs[0])
                .nickname(logs[1])
                .loggingTime(logs[2])
                .build();
    }
}
