package com.hsbc.incident.config;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MessageSourceFactory {

    private static MessageSource errorMessageSource;

    public static MessageSource getErrorMessageSource() {
        if (errorMessageSource == null) {
            ResourceBundleMessageSource errorMessageSource = new ResourceBundleMessageSource();
            errorMessageSource.setBasename("errorcode");
            errorMessageSource.setDefaultEncoding("UTF-8");
            MessageSourceFactory.errorMessageSource = errorMessageSource;
        }
        return errorMessageSource;
    }
}