package com.hsbc.incident.shared.utils;

import java.util.Optional;

public class NumericUtils {

    public static Optional<Long> parseLong(String value) {
        try {
            return Optional.of(Long.parseLong(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
