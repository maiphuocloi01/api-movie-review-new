package com.example.moviereview.controller;

import com.example.moviereview.model.Otp;
import com.example.moviereview.service.OtpService;
import com.example.moviereview.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/api/v1/auth/otp")
@AllArgsConstructor @Slf4j
public class OtpController {
    private final OtpService otpService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Otp> createOtp(@RequestBody HashMap<String, String> request) {
        Otp newOtp = otpService.createOtp(request.get("email"));
        return ResponseEntity.status(HttpStatus.CREATED).body(newOtp);
    }

    @PutMapping("/confirm")
    public ResponseEntity<Object> confirmOtp(@RequestBody HashMap<String, String> request) {
        HashMap<String, String> response = new HashMap<>();
        try {
            Boolean isConfirmed = otpService.confirmOtp(request.get("email"), request.get("token"));
            userService.enableUserByEmail(request.get("email"));
            response.put("confirm", isConfirmed.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
