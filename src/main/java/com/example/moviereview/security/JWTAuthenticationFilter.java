package com.example.moviereview.security;

import com.example.moviereview.model.User;
import com.example.moviereview.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(403);
        Map<String, String> error = new HashMap<>();
        if (failed.getMessage().equals("Bad credentials")) {
            error.put("error", "Wrong password");
        } else {
            error.put("error", failed.getMessage());
        }
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication)
            throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        String access_token = JwtUtil.encodeToken(user);
        Map<String, String> token = new HashMap<>();
        token.put("access_token", access_token);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        new ObjectMapper().writeValue(response.getOutputStream(), token);
    }
}
