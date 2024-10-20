package com.hsbc.incident.domain.service;

import com.hsbc.incident.domain.model.entity.User;
import com.hsbc.incident.domain.model.value.IdToken;
import com.hsbc.incident.domain.repository.UserRepository;
import com.hsbc.incident.shared.BusinessException;
import com.hsbc.incident.shared.constant.CommonConstant;
import com.hsbc.incident.shared.constant.ErrorCode;
import com.hsbc.incident.shared.utils.DateUtils;
import com.hsbc.incident.shared.utils.JwtUtils;
import java.util.Date;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepo;

    // return JWT token if login is successful
    // throw exception if login is unsuccessful
    public IdToken login(String username, String password) {
        return userRepo.findByUsername(username)
            .filter(user -> user.getPassword().equals(password))
            .map(user -> {
                log.info("User {} logged in successfully", username);
                Date expiryDate = new Date(
                    new Date().getTime() + CommonConstant.ID_TOKEN_EXPIRATION_TIME_IN_SECONDS);
                String token = JwtUtils.generateToken(user.getId(), expiryDate, new HashMap<>());
                return new IdToken()
                    .setToken(token)
                    .setExpiresAt(DateUtils.dateToZonedDateTime(expiryDate));
            })
            .orElseThrow(() -> BusinessException.unauthorized(ErrorCode.LOGIN_FAILED, null));
    }

    public Long validateToken(String token) {
        return JwtUtils.getUserIdFromToken(token);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
            .orElseThrow(
                () -> BusinessException.notFound(ErrorCode.USER_NOT_FOUND, String.valueOf(id)));
    }

}
