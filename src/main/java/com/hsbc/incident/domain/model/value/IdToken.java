package com.hsbc.incident.domain.model.value;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class IdToken {

    private String token;
    private ZonedDateTime expiresAt;
}
