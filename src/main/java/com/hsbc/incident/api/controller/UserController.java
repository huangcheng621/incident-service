package com.hsbc.incident.api.controller;

import com.hsbc.incident.api.request.UserLoginRequest;
import com.hsbc.incident.api.response.UserLoginResponse;
import com.hsbc.incident.domain.model.value.IdToken;
import com.hsbc.incident.domain.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest request) {
        IdToken idToken = userService.login(request.getUsername(), request.getPassword());
        return new UserLoginResponse()
            .setToken(idToken.getToken())
            .setExpiresAt(idToken.getExpiresAt());
    }
}
