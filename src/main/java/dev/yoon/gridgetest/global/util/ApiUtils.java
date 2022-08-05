package dev.yoon.gridgetest.global.util;


import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.error.ErrorResponse;

public class ApiUtils {

    private ApiUtils() {
        throw new AssertionError();
    }

    public static <T> ApiResult<T> success(T response, String message) {
        return new ApiResult<>(true, response, message, null);
    }

    public static <T> ApiResult<T> error(ErrorResponse errorResponse) {
        return new ApiResult<>(false, null, null, errorResponse);
    }


}