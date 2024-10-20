package com.hsbc.incident.config;

import com.hsbc.incident.api.response.ErrorResponse;
import com.hsbc.incident.shared.BusinessException;
import com.hsbc.incident.shared.constant.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<ErrorResponse> handleBusinessException(
        BusinessException ex) {
        if (ex.getHttpStatus().is5xxServerError()) {
            log.error("Error:", ex);
        } else if (!Objects.isNull(ex.getErrorCode())) {
            log.info("Error code: {}, error message: {}", ex.getErrorCode().name(),
                ex.getErrorCode().getDescription());
            log.trace("Error details: ", ex);
        }
        return ResponseEntity.status(ex.getHttpStatus())
            .body(ErrorResponse.of(ex.getErrorCode(), ex.getDetails()));
    }

    @ExceptionHandler(value = {BindException.class})
    protected ResponseEntity<ErrorResponse> handleBindException(final BindException ex) {
        List<Object> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + ":" + errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(ErrorCode.INVALID_REQUEST, errors));
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
        final HttpMessageConversionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.of(ErrorCode.INVALID_REQUEST));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Unexpected error:", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.of(ErrorCode.UNKNOWN, List.of(ex.getMessage())));
    }

}
