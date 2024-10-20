package com.hsbc.incident.shared.constant;

public class CommonConstant {

    public static final String[] AUTH_WHITELIST = {
        "/v1/users/login",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    };

    public static final long ID_TOKEN_EXPIRATION_TIME_IN_SECONDS = 3600;
    public static final String CONTEXT_LOGIN_USER_ID = "LOGIN_USER_ID";
    public static final String MDC_TRACE_ID = "requestId";
}
