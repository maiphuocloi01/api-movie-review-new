package com.example.moviereview.firebase;

import com.example.moviereview.model.TokenModel;
import com.example.moviereview.model.User;
import com.google.firebase.auth.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class FirebaseProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            TokenModel token = (TokenModel) authentication;
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token.getToken(), true);
            String uid = firebaseToken.getUid();
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
            return new User(userRecord);
        } catch (FirebaseAuthException e) {
            logger.error("Fail: {}", getErrorCode(e.getAuthErrorCode()));
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(TokenModel.class);
    }

    private String getErrorCode(AuthErrorCode errorCode) {
        String error;
        switch (errorCode.toString()) {
            case "EXPIRED_ID_TOKEN":
                error = "token expired";
                break;
            case "INVALID_ID_TOKEN":
                error = "token invalid";
                break;
            case "REVOKED_ID_TOKEN":
                error = "token revoked";
                break;
            case "CONFIGURATION_NOT_FOUND":
                error = "config not found";
                break;
            default:
                error = "authentication fail";
        }
        return error;
    }
}
