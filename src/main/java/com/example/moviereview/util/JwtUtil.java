package com.example.moviereview.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.moviereview.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtil {

    private static final String SECRET = "ncihvicxKtiUZGXeGCH0HvmhXNIHt82N";
    public static final long EXPIRATION_TIME = (60 * 24 * 60 * 30 * 1000L); // 30 days

    public static String encodeToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("user_id", user.getId())
                .sign(algorithm);
    }

    public static HashMap<String, String> decodeToken(String authToken) throws Exception {
        HashMap<String, String> decoded = new HashMap<>();
        try {
            String token = authToken.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String email = decodedJWT.getSubject();
            String userId = decodedJWT.getClaim("user_id").asString();
            decoded.put("email", email);
            decoded.put("userId", userId);
            return decoded;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
