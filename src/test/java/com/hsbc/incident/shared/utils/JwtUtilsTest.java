package com.hsbc.incident.shared.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class JwtUtilsTest {

    @Test
    void shouldGenerateTokenWithUserIdAsSubject() {
        Date expirationDate = new Date(2023, 12, 31);
        String token = JwtUtils.generateToken(123456L, expirationDate, new HashMap<>());

        Long userId = JwtUtils.getUserIdFromToken(token);
        assertThat(userId).isEqualTo(123456L);
    }
}