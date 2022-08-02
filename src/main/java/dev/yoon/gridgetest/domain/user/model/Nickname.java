package dev.yoon.gridgetest.domain.user.model;

import dev.yoon.gridgetest.global.error.exception.AuthenticationException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nickname {

    @Column(name = "nickname", length = 200)
    private String value;

    @Column(name = "nickname_update_date")
    private LocalDate updateDate;

    @Column(name= "nickname_update_count", nullable = false)
    private Integer updatedCount;

    @Builder
    public Nickname(final String value) {
        this.value = value;
        this.updatedCount = 0;
    }

    public static Nickname from(String value) {
        return Nickname.builder()
                .value(value)
                .build();
    }

    public void changeNickname(final String name) {

        if (!this.value.equals(name)) {
            if (updatedCount == 2) {
                if (!isValidUpdateTime()) {
                    throw new AuthenticationException(ErrorCode.CANT_CHANGE_2_WEEKS);
                }
            }
            this.value = name;
            UpdateDate();
            UpdateCount();
        }


    }

    private void UpdateDate() {
        if (this.updatedCount == 0) {
            this.updateDate = LocalDate.now();
        }
    }

    private void UpdateCount() {
        if (this.updatedCount == 0 || this.updatedCount == 1){
            increaseUpdateCount();
        }else {
            this.updatedCount = 0;
        }
    }

    private void increaseUpdateCount() {
        this.updatedCount++;
    }
    public boolean isValidUpdateTime() {
        return ChronoUnit.DAYS.between(updateDate, LocalDate.now()) > 14;
    }

    public void isValidUpdate() {
        if (updatedCount == 2) {
            if (!isValidUpdateTime()) {
                throw new AuthenticationException(ErrorCode.CANT_CHANGE_2_WEEKS);
            }
        }
    }

}
