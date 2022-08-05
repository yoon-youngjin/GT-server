package dev.yoon.gridgetest.domain.report.model;

import lombok.Getter;

@Getter
public enum ServiceType {

    BOARD("board"), ANSWER("answer"), MESSAGE("message"),
    REPORT("report"), USER("users");

    private String value;

    ServiceType(String value) {
        this.value = value;
    }

}
