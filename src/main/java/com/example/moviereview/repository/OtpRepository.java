package com.example.moviereview.repository;

import com.example.moviereview.model.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends MongoRepository<Otp, String> {
    Otp findTopByEmailOrderByCreatedAtDesc(String email);
}
