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
public class Name {

    @Column(name = "name", length = 20, nullable = false)
    private String value;

    @Column(name = "name_update_date")
    private LocalDate updateDate;

    @Column(name = "name_update_count", nullable = false)
    private Integer updatedCount;

    @Builder
    public Name(final String value) {
        this.value = value;
        this.updatedCount = 0;
    }

    public static Name from(String value) {
        return Name.builder()
                .value(value)
                .build();
    }

    public void changeName(final String name) {

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
        if (this.updatedCount == 0 || this.updatedCount == 1) {
            increaseUpdateCount();
        } else {
            this.updatedCount = 0;
        }
    }

    private void increaseUpdateCount() {
        this.updatedCount++;
    }

    private boolean isValidUpdateTime() {
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
