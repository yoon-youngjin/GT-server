package dev.yoon.gridgetest.global.error;

import dev.yoon.gridgetest.global.ApiResult;
import dev.yoon.gridgetest.global.error.exception.BusinessException;
import dev.yoon.gridgetest.global.error.exception.FeignClientException;
import dev.yoon.gridgetest.global.util.ApiUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * javax.validation.Valid 또는 @Validated binding error가 발생할 경우
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ApiResult<ErrorResponse>> handleBindException(BindException e) {
        log.error("handleBindException", e);

        List<String> errorMessages = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.add(fieldError.getDefaultMessage());
            }
        }

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, errorMessages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiUtils.error(errorResponse));
    }

    /**
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ApiResult<ErrorResponse>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        List<String> errorMessages = List.of(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiUtils.error(errorResponse));
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiResult<ErrorResponse>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        List<String> errorMessages = List.of(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, errorMessages);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ApiUtils.error(errorResponse));
    }

    /**
     * 비즈니스 로직 실행 중 오류 발생
     */
    @ExceptionHandler(value = { BusinessException.class })
    protected ResponseEntity<ApiResult<ErrorResponse>> handleConflict(BusinessException e) {

        log.error("BusinessException", e);
        List<String> errorMessages = List.of(e.getMessage());
        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatus());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, errorMessages);
        return ResponseEntity.status(httpStatus).body(ApiUtils.error(errorResponse));
    }

    /**
     * FeignClient 예외 발생
     */
    @ExceptionHandler(FeignClientException.class)
    protected ResponseEntity<ApiResult<ErrorResponse>> handleFeignClientException(FeignClientException e) {
        log.error("FeignClientException", e);
        List<String> errorMessages = List.of(e.getMessage());
        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatus());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, errorMessages);

        return ResponseEntity.status(httpStatus).body(ApiUtils.error(errorResponse));
    }

    /**
     * 나머지 예외 발생
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiResult<ErrorResponse>> handleException(Exception e) {
        log.error("Exception", e);
        List<String> errorMessages = List.of(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, errorMessages);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiUtils.error(errorResponse));
    }

}