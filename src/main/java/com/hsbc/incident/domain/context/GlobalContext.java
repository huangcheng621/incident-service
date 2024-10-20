package com.hsbc.incident.domain.context;

import com.hsbc.incident.shared.constant.CommonConstant;
import java.util.Locale;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GlobalContext {

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    public static Optional<Long> getLoginUserIdFromContext() {
        ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            return Optional.ofNullable(
                (Long) request.getAttribute(CommonConstant.CONTEXT_LOGIN_USER_ID));
        } else {
            return Optional.of(1L);
        }
    }
}
