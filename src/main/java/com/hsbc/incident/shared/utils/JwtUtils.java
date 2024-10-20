package com.hsbc.incident.shared.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.hsbc.incident.shared.constant.ErrorCode;
import com.hsbc.incident.shared.BusinessException;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    // Replace with your own secret key. Normally it would be stored in a secure location.
    private static final String SECRET = "your_secret_key";
    public static final String ISSUER = "www.hsbc.com";
    public static final JWTVerifier VERIFIER = JWT.require(Algorithm.HMAC256(SECRET))
        .withIssuer(ISSUER)
        .build();
    private static final String JWT_CLAIM_KEY = "data";
    private static final String JWT_TYPE_KEY = "typ";


    public static String generateToken(long userId, Date expiryDate, Map<String, Object> data) {
        JWTCreator.Builder builder = JWT.create()
            .withHeader(Map.of(JWT_TYPE_KEY, "JWT"))
            .withSubject(String.valueOf(userId))
            .withIssuedAt(new Date())
            .withIssuer(JwtUtils.ISSUER)
            .withClaim(JWT_CLAIM_KEY, data)
            .withExpiresAt(expiryDate);
        return builder.sign(Algorithm.HMAC256(JwtUtils.SECRET));
    }

    public static Long getUserIdFromToken(String jwtString) {
        try {
            DecodedJWT jwt = VERIFIER.verify(jwtString);
            String subject = jwt.getSubject();
            return Long.parseLong(subject);
        } catch (Exception e) {
            throw BusinessException.unauthorized(ErrorCode.INVALID_TOKEN, "");
        }
    }

}
