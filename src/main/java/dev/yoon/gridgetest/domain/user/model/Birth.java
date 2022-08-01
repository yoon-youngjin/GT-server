package dev.yoon.gridgetest.domain.user.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Birth {

    @Column(name = "year", length = 10)
    private String year;

    @Column(name = "month", length = 10)
    private String month;

    @Column(name = "day", length = 10)
    private String day;

    @Builder
    public Birth(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getBirthDay() {
        return year + "년 " + month + "월 " + day + "일";
    }

    public static Birth toBirth(String birth) {

        String[] st = birth.split(" ");
        return Birth.builder()
                .year(st[0].substring(0, st[0].length() - 1))
                .month(st[1].substring(0, st[1].length() - 1))
                .day(st[2].substring(0, st[2].length() - 1))
                .build();
    }


}
