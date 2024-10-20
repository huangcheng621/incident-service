package com.hsbc.incident.domain.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hsbc.incident.domain.model.entity.User;
import com.hsbc.incident.domain.model.value.IdToken;
import com.hsbc.incident.domain.repository.UserRepository;
import com.hsbc.incident.shared.BusinessException;
import com.hsbc.incident.shared.constant.ErrorCode;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    // Test should return token and user ID when login is successful.
    @Test
    void shouldReturnTokenAndUserIdWhenLoginSuccess() {
        String username = "testUser";
        String password = "testPassword";
        User user = new User().setUsername(username).setPassword(password).setId(1L);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        IdToken result = userService.login(username, password);
        Assertions.assertThat(result.getToken()).isNotNull();
        Assertions.assertThat(result.getExpiresAt()).isNotNull();
    }

    // Test should throw an exception when the username is not found.
    @Test
    void shouldThrowExceptionWhenUsernameNotFound() {
        String username = "wrongUser";
        String password = "testPassword";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
            () -> userService.login(username, password));

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.LOGIN_FAILED);
        Assertions.assertThat(exception.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    // Test should throw an exception when the password is invalid.
    @Test
    void shouldThrowExceptionWhenInvalidPassword() {
        String username = "testUser";
        String password = "wrongPassword";
        User user = new User().setUsername(username).setPassword("correctPassword").setId(1L);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        BusinessException exception = assertThrows(BusinessException.class,
            () -> userService.login(username, password));
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.LOGIN_FAILED);
        Assertions.assertThat(exception.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    // Test should throw an exception when the token is invalid.
    @Test
    void shouldThrowExceptionWhenInvalidToken() {
        String invalidToken = "invalidToken";

        BusinessException exception = assertThrows(BusinessException.class,
            () -> userService.validateToken(invalidToken));
        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.INVALID_TOKEN);
    }

    // Test should return the user when the user exists by ID.
    @Test
    void shouldReturnUserWhenUserExistsById() {
        Long userId = 1L;
        User user = new User().setId(userId).setUsername("testUser");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.getUserById(userId);

        Assertions.assertThat(foundUser.getId()).isEqualTo(userId);
    }

    // Test should throw an exception when the user is not found by ID.
    @Test
    void shouldThrowExceptionWhenUserNotFoundById() {
        Long userId = 1L;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
            () -> userService.getUserById(userId));

        Assertions.assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_FOUND);
    }
}
