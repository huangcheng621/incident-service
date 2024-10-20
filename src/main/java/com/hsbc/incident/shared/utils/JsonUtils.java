package com.hsbc.incident.shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(new JavaTimeModule());

    public static String toJsonString(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        return objectMapper.writeValueAsString(object);

    }
}
