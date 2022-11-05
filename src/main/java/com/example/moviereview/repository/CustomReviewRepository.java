package com.example.moviereview.repository;

import com.example.moviereview.model.Review;

import java.util.List;

public interface CustomReviewRepository {
    void updateReactReviewByUserId(String reviewId, String userId, String action);

    List<Review> findReviewsByAuthorId(String authorId);

}
