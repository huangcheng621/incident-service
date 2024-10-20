package com.hsbc.incident.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.hsbc.incident.domain.model.entity.User;
import com.hsbc.incident.domain.model.value.Permission;
import com.hsbc.incident.domain.service.UserService;
import com.hsbc.incident.shared.BusinessException;
import com.hsbc.incident.shared.constant.CommonConstant;
import com.hsbc.incident.shared.constant.ErrorCode;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {

    @Mock
    private UserService userService;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    private void setAuthorizationHeader(MockHttpServletRequest request, String token) {
        request.addHeader("Authorization", "Bearer " + token);
    }

    // 测试正常情况下的授权
    @Test
    public void shouldProceedRequestWhenAuthorizationHeaderIsValid() throws Exception {
        String token = "someValidToken";
        Long userId = 1L;
        User user = Mockito.mock(User.class);
        lenient().when(user.getId()).thenReturn(userId);
        when(user.getPermissions()).thenReturn(Set.of(Permission.CREATE_INCIDENT));
        when(userService.validateToken(token)).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(user);
        setAuthorizationHeader(request, token);
        request.setMethod("GET");
        request.setRequestURI("/api/vi/incidents");

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Assertions.assertThat(auth.isAuthenticated()).isTrue();
        Assertions.assertThat(auth.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .map(Permission::valueOf)
            .collect(Collectors.toSet())).containsExactly(Permission.CREATE_INCIDENT);

        Assertions.assertThat(MDC.get(CommonConstant.MDC_TRACE_ID)).isNotNull();
        Assertions.assertThat(request.getAttribute(CommonConstant.CONTEXT_LOGIN_USER_ID))
            .isEqualTo(userId);
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    // 测试授权白名单路径
    @ParameterizedTest
    @ValueSource(strings = {
        "/v1/users/login",
        "/swagger-ui/**",
        "/v3/api-docs/**"})
    public void shouldProceedRequestWhenAuthorizationHeaderIsValid(String requestURI)
        throws Exception {
        String token = "someValidToken";
        Long userId = 1L;
        User user = Mockito.mock(User.class);
        lenient().when(user.getId()).thenReturn(userId);
        when(user.getPermissions()).thenReturn(Set.of(Permission.CREATE_INCIDENT));
        when(userService.validateToken(token)).thenReturn(userId);
        when(userService.getUserById(userId)).thenReturn(user);
        setAuthorizationHeader(request, token);
        request.setMethod("GET");
        request.setRequestURI(requestURI);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void shouldBlockRequestWhenAuthorizationHeaderIsInvalid() throws Exception {
        String token = "someInvalidToken";
        when(userService.validateToken(token))
            .thenThrow(BusinessException.unauthorized(ErrorCode.INVALID_TOKEN, ""));

        setAuthorizationHeader(request, token);
        request.setMethod("GET");
        request.setRequestURI("/api/vi/incidents");

        BusinessException ex = assertThrows(BusinessException.class,
            () -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));
        Assertions.assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_TOKEN);
        Assertions.assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
