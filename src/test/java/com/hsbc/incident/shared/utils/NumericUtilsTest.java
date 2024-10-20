package com.hsbc.incident.shared.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class NumericUtilsTest {

    @Test
    void shouldReturnValueIfParseLongSuccess() {
        Optional<Long> result = NumericUtils.parseLong("123");
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(123L);
    }

    @Test
    void shouldReturnEmptyIfParseLongFails() {
        Optional<Long> result = NumericUtils.parseLong("12a");
        assertThat(result).isEmpty();
    }
}