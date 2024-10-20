package com.hsbc.incident.domain.context;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hsbc.incident.shared.constant.CommonConstant;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class GlobalContextTest {

    // Test should return an empty Optional when request attributes are null.
    @Test
    void shouldReturnEmptyOptionalWhenRequestAttributesNull() {
        RequestContextHolder.setRequestAttributes(null);

        Assertions.assertThat(GlobalContext.getLoginUserIdFromContext()).isEmpty();
    }

    @Test
    public void shouldReturnCurrentUserIdWhenGetLoginUserIdFromContext() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute(CommonConstant.CONTEXT_LOGIN_USER_ID)).thenReturn(1L);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Optional<Long> result = GlobalContext.getLoginUserIdFromContext();

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(1L);
    }
}