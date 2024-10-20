package com.hsbc.incident.shared.constant;

public enum ErrorCode {

    INVALID_REQUEST,
    LOGIN_FAILED,
    UNAUTHORIZED,
    FORBIDDEN,
    INVALID_TOKEN,
    INCIDENT_NOT_FOUND,
    USER_NOT_FOUND,
    UNKNOWN;

    public String getDescription() {
        return this.name();
    }
}
