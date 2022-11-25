package com.example.moviereview.firebase;

import com.example.moviereview.model.User;
import com.example.moviereview.repository.FirebaseRepository;
import com.example.moviereview.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class FirebaseFilter extends BasicAuthenticationFilter {

    FirebaseRepository firebaseRepository;
    private static final Logger logger = LoggerFactory.getLogger(FirebaseFilter.class);

    public FirebaseFilter(AuthenticationManager authenticationManager, FirebaseRepository firebaseRepository) {
        super(authenticationManager);
        this.firebaseRepository = firebaseRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        try {
            String token = getToken(request);
            if (token != null) {
                if (token.length() > 189) {
                    logger.info("Token1: {}", token);
                    FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                    Collection<SimpleGrantedAuthority> authority = Collections.singleton(new SimpleGrantedAuthority("USER"));
                    User findUser = firebaseRepository.findUserByEmail(decodedToken.getEmail());
                    if (findUser != null) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(decodedToken.getEmail(), findUser.getId(), authority);
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                    //SecurityContextHolder.getContext().setAuthentication(new TokenModel(token));
                } else {
                    logger.info("Token2: {}", token);
                    HashMap<String, String> decodedJWT = JwtUtil.decodeToken(authorizationHeader);
                    String email = decodedJWT.get("email");
                    String userId = decodedJWT.get("userId");
                    Collection<SimpleGrantedAuthority> authority = Collections.singleton(new SimpleGrantedAuthority("USER"));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, userId, authority);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception exception) {
            logger.error("Error logging in: {}", exception.getMessage());
            Map<String, String> error = new HashMap<>();
            error.put("error", exception.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header != null && header.startsWith("Bearer "))
            return header.replace("Bearer ", "");
        return null;
    }

}
