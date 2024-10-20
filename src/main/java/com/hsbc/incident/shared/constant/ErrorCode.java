package com.hsbc.incident.shared.constant;

import com.hsbc.incident.config.MessageSourceFactory;
import com.hsbc.incident.domain.context.GlobalContext;

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
        String messageKey = "errorcode." + this.name().toLowerCase();
        return MessageSourceFactory.getErrorMessageSource().getMessage(
            messageKey, null, GlobalContext.getLocale());
    }
}
