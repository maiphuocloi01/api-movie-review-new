package com.example.moviereview.controller;

import com.example.moviereview.model.User;
import com.example.moviereview.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<Object> getUserInfo() {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/me/statistics")
    public ResponseEntity<?> getStatistics() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        HashMap<String, Double> statistics = userService.getStatisticsByUserId(userId);
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserInfoAndReviews(@PathVariable("userId") String userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{userId}/statistics")
    public ResponseEntity<?> getStatisticsByUserId(@PathVariable("userId") String userId) {
        HashMap<String, Double> statistics = userService.getStatisticsByUserId(userId);
        return ResponseEntity.ok(statistics);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateInfoUser(@RequestBody HashMap<String, String> request) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info(email);
            userService.updateUserById(userId, request);
            userService.enableUserByEmail(email);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> changePassword(@RequestBody HashMap<String, String> request) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            userService.changePasswordById(userId, request.get("oldPassword"), request.get("newPassword"));
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
