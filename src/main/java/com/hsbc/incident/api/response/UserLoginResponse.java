package com.hsbc.incident.api.response;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserLoginResponse {

    private String token;
    private ZonedDateTime expiresAt;
}
