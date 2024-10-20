package com.hsbc.incident.api.response;

import com.hsbc.incident.shared.constant.ErrorCode;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;
    private List<Object> details;

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getDescription(), List.of());
    }

    public static ErrorResponse of(ErrorCode errorCode, List<Object> details) {
        return new ErrorResponse(errorCode.name(), errorCode.getDescription(), details);
    }
}
