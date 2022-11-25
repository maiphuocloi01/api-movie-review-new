package com.example.moviereview.controller;

import com.example.moviereview.model.User;
import com.example.moviereview.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Object> register(@RequestBody User request) {
        try {
            User user = userService.signUpUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login-with-google")
    public ResponseEntity<Object> loginWithGoogle(@RequestBody User request) {
        try {
            User userExist = userService.checkEmailExist(request.getEmail());
            if(userExist != null){
                return ResponseEntity.status(HttpStatus.OK).body(userExist);
            } else {
                User user = userService.signUpUserByGoogle(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(user);
            }
        } catch (Exception e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/registration/enable")
    public ResponseEntity<Object> enableUser(@RequestBody HashMap<String, String> request) {
        try {
            userService.enableUserByEmail(request.get("email"));
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody HashMap<String, String> request) {
        try {
            userService.changePasswordByEmail(request.get("email"), request.get("password"));
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
