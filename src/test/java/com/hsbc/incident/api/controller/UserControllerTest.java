package com.hsbc.incident.api.controller;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hsbc.incident.api.request.UserLoginRequest;
import com.hsbc.incident.config.JwtAuthenticationFilter;
import com.hsbc.incident.domain.model.value.IdToken;
import com.hsbc.incident.domain.service.UserService;
import com.hsbc.incident.shared.BusinessException;
import com.hsbc.incident.shared.constant.ErrorCode;
import com.hsbc.incident.shared.utils.JsonUtils;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = {JwtAuthenticationFilter.class}))
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldReturnIdTokenGivenValidCredentialsWhenLogin() throws Exception {
        String username = "testuser";
        String password = "testpassword";
        String jwtToken = "exampleJwtToken";
        ZonedDateTime expiresAt = ZonedDateTime.of(2021, 12, 31, 23, 59, 59, 0,
            ZoneId.of("UTC"));

        UserLoginRequest request = new UserLoginRequest()
            .setUsername(username)
            .setPassword(password);

        when(userService.login(eq(username), eq(password)))
            .thenReturn(new IdToken().setToken(jwtToken).setExpiresAt(expiresAt));

        mockMvc.perform(post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJsonString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value(jwtToken))
            .andExpect(jsonPath("$.expiresAt").value("2021-12-31T23:59:59Z"));
    }

    @Test
    public void shouldReturn401GivenInvalidCredentialsWhenLogin() throws Exception {
        String username = "testuser";
        String password = "testpassword";

        UserLoginRequest request = new UserLoginRequest()
            .setUsername(username)
            .setPassword(password);

        when(userService.login(eq(username), eq(password)))
            .thenThrow(BusinessException.unauthorized(ErrorCode.LOGIN_FAILED, username));

        mockMvc.perform(post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJsonString(request)))
            .andExpect(status().isUnauthorized());
    }
}