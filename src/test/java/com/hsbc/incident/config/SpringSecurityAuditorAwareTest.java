package com.hsbc.incident.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.hsbc.incident.domain.service.UserService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SpringSecurityAuditorAwareTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private SpringSecurityAuditorAware auditorAware;

    @Test
    public void shouldReturnCurrentUserIdWhenGetCurrentAuditor() {
        when(userService.getLoginUserIdFromContext()).thenReturn(Optional.of(1L));

        Optional<Long> result = auditorAware.getCurrentAuditor();

        assertTrue(result.isPresent());
        assertEquals(1L, result.get());
    }
}
