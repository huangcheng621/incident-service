package com.hsbc.incident.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hsbc.incident.shared.constant.CommonConstant;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
public class SpringSecurityAuditorAwareTest {

    @InjectMocks
    private SpringSecurityAuditorAware auditorAware;

    @Test
    public void shouldReturnCurrentUserIdWhenGetCurrentAuditor() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute(CommonConstant.CONTEXT_LOGIN_USER_ID)).thenReturn(1L);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Optional<Long> result = auditorAware.getCurrentAuditor();

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(1L);
    }
}
