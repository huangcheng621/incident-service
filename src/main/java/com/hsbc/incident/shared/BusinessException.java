package com.hsbc.incident.shared;

import com.hsbc.incident.shared.constant.ErrorCode;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Accessors(chain = true)
@Getter
public class BusinessException extends RuntimeException implements Serializable {

    private final ErrorCode errorCode;

    private final HttpStatus httpStatus;

    private final List<Object> details;

    private BusinessException(ErrorCode errorCode, HttpStatus httpStatus, List<Object> args) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.details = args;
    }

    public static BusinessException unauthorized(ErrorCode errorCode, String msg) {
        if (msg == null) {
            return new BusinessException(errorCode, HttpStatus.UNAUTHORIZED, List.of());
        }
        return new BusinessException(errorCode, HttpStatus.UNAUTHORIZED, List.of(msg));
    }

    public static BusinessException notFound(ErrorCode errorCode, String msg) {
        if (msg == null) {
            return new BusinessException(errorCode, HttpStatus.NOT_FOUND, List.of());
        }
        return new BusinessException(errorCode, HttpStatus.NOT_FOUND, List.of(msg));
    }

    public static BusinessException badRequest(ErrorCode errorCode, String msg) {
        if (msg == null) {
            return new BusinessException(errorCode, HttpStatus.BAD_REQUEST, List.of());
        }
        return new BusinessException(errorCode, HttpStatus.BAD_REQUEST, List.of(msg));
    }
}
