package com.hsbc.incident.config;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(
        Authentication auth, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException("Unsupported permission evaluator");
    }

    @Override
    public boolean hasPermission(
        Authentication auth, Serializable targetId, String targetType, Object permission) {
        // TODO: to be implemented
        return true;
    }
}
