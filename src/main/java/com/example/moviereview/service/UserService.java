package com.example.moviereview.service;

import com.example.moviereview.model.User;

import java.util.HashMap;

public interface UserService {
    User signUpUser(User user);
    void enableUserByEmail(String email);
    void changePasswordByEmail(String email, String password);
    User getUserByEmail(String email);
    User getUserById(String userId);
    HashMap<String, Double> getStatisticsByUserId(String userId);
    void updateUserById(String userId, HashMap<String, String> updateFields);
    void updateImageById(String userId, HashMap<String, String> updateFields);
    void changePasswordById(String userId, String oldPassword, String newPassword);
}
