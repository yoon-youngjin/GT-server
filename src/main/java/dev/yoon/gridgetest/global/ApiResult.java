package dev.yoon.gridgetest.global;

import dev.yoon.gridgetest.global.error.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResult<T> {

    private final boolean success;
    private final T response;
    private final String message;
    private final ErrorResponse errorResponse;

    public ApiResult(boolean success, T response, String message, ErrorResponse errorResponse) {

        this.success = success;
        this.message = message;
        this.response = response;
        this.errorResponse = errorResponse;

    }
}