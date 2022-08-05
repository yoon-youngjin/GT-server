package dev.yoon.gridgetest.domain.admin.dto.log;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetLogRes {


    private String content;

    private String nickname;

    private String loggingTime;

    @Builder
    public GetLogRes(String content, String nickname, String loggingTime) {

        this.content = content;
        this.nickname = nickname;
        this.loggingTime = loggingTime;
    }


    public static GetLogRes from(String log) {
        String[] logs = log.split("/");

        return GetLogRes.builder()
                .content(logs[0])
                .nickname(logs[1])
                .loggingTime(logs[2])
                .build();
    }
}
