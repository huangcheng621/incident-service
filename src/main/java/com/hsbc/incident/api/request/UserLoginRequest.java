package com.hsbc.incident.api.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserLoginRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}