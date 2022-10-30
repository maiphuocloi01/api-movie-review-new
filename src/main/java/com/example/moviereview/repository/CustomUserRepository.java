package com.example.moviereview.repository;

import com.example.moviereview.model.User;

import java.util.HashMap;

public interface CustomUserRepository {
    void updateInfoUserById(String userId, HashMap<String, String> updateFields);
}
