package com.hsbc.incident.config;

import com.hsbc.incident.domain.context.GlobalContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("auditorAware")
@Slf4j
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return GlobalContext.getLoginUserIdFromContext();
    }
}