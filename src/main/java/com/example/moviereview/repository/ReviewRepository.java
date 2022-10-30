package com.example.moviereview.repository;

import com.example.moviereview.model.Review;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findReviewsByMovieId(String movieId);
//    List<Review> findReviewsByAuthor_Id(String author);
    Review  findReviewById(String id);
}
