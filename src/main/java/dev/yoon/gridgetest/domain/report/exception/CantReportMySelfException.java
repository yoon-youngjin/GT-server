package dev.yoon.gridgetest.domain.report.exception;


import dev.yoon.gridgetest.global.error.exception.EntityNotFoundException;
import dev.yoon.gridgetest.global.error.exception.ErrorCode;

public class CantReportMySelfException extends EntityNotFoundException {

    public CantReportMySelfException(ErrorCode errorCode) {
        super(errorCode);
    }
}
