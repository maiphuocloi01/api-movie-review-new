package com.example.moviereview.controller;

import com.example.moviereview.model.Review;
import com.example.moviereview.model.User;
import com.example.moviereview.model.UserInfo;
import com.example.moviereview.service.ReviewService;
import com.example.moviereview.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/movies/{movieId}")
    public List<Review> getReviewsByMovieId(@PathVariable("movieId") String movieId) {
        return reviewService.getReviewsByMovieId(movieId);
    }

    @GetMapping("/users/{userId}")
    public List<Review> getReviewsByUserId(@PathVariable("userId") String userId) {
        return reviewService.getReviewsByUserId(userId);
    }

    @GetMapping("/users/{userId}/{movieId}")
    public ResponseEntity<Object> getReviewsByUserIdAndMovieId(@PathVariable("userId") String userId, @PathVariable("movieId") String movieId) {
        Optional<Review> result = reviewService.getReviewsByUserIdAndMovieId(userId, movieId);
        if (result.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(result.get());
        } else {
            HashMap<String,String> response = new HashMap<>();
            response.put("error", "Review not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping(value = "/{reviewId}")
    public ResponseEntity<Object> getReviewById(@PathVariable("reviewId") String reviewId) {
        try {
            Review review = reviewService.getReviewById(reviewId);
            return ResponseEntity.status(HttpStatus.OK).body(review);
        } catch (IllegalStateException e) {
            HashMap<String,String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping
    public ResponseEntity<Review> postReview(@RequestBody Review review) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        UserInfo author = new UserInfo(userId);
        Review newReview = reviewService.addNewReview(review.getContent(), review.getMovie(), review.getStars(), author);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.getReviewById(newReview.getId()));
    }

    @PutMapping(path = "/{reviewId}")
    public ResponseEntity<?> updateReview (@PathVariable("reviewId") String reviewId, @RequestBody Review newReview) {
        try {
            return ResponseEntity.ok(reviewService.updateReview(reviewId, newReview));
        } catch (IllegalStateException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping(path = "/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") String reviewId) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            reviewService.deleteReview(reviewId, userId);
        } catch (IllegalStateException e) {
            log.error(e.getMessage());
        }
    }

    @PutMapping(path = "/{reviewId}/{action}")
    public void reactReview(@PathVariable("reviewId") String reviewId, @PathVariable("action") String action) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            reviewService.reactReviewByUserId(reviewId, userId, action);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

//    @PostMapping(path = "/reviews/dislike/{reviewId}/{userId}")
//    public Review dislikeReview(@PathVariable("reviewId") String reviewId, @PathVariable("userId") String userId) {
//        return reviewService.dislikeReviewByUserId(reviewId, userId);
//    }
//
//    @PostMapping(path = "/reviews/unlike/{reviewId}/{userId}")
//    public Review unlikeReview(@PathVariable("reviewId") String reviewId, @PathVariable("userId") String userId) {
//        return reviewService.unlikeReviewByUserId(reviewId, userId);
//    }
//
//    @PostMapping(path = "/reviews/undislike/{reviewId}/{userId}")
//    public Review unDislikeReview(@PathVariable("reviewId") String reviewId, @PathVariable("userId") String userId) {
//        return reviewService.unDislikeReviewByUserId(reviewId, userId);
//    }

}
