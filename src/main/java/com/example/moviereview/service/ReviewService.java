package com.example.moviereview.service;

import com.example.moviereview.model.Movie;
import com.example.moviereview.model.Review;
import com.example.moviereview.model.User;
import com.example.moviereview.model.UserInfo;
import com.example.moviereview.repository.CustomReviewRepository;
import com.example.moviereview.repository.ReviewRepository;
import com.example.moviereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomReviewRepository customReviewRepository;
    private final UserRepository userRepository;
    public List<Review> getReviewsByMovieId(String movieId) {
        return reviewRepository.findReviewsByMovieId(movieId);
    }

    public List<Review> getReviewsByUserId(String userId) {
        User user = userRepository.findUserById(userId);
        return user.getReviews();
    }

    public Optional<Review> getReviewsByUserIdAndMovieId(String userId, String movieId) {
        return reviewRepository.findReviewsByMovieId(movieId).stream().filter(obj ->
            obj.getAuthor().getId().equals(userId)).findFirst();
    }
    public Review getReviewById(String reviewId) {
        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) {
            throw new IllegalStateException("review not found");
        }
        return review;
    }



    public Review addNewReview(String content, Movie movie, Double stars, UserInfo author) {
        if (author == null) {
            throw new IllegalStateException("please login/register before you add review");
        }
        if (stars == 0 || content.isBlank()) {
            throw new IllegalStateException("please review before you post");
        }
        Review review = new Review(content, movie, author, stars);
        reviewRepository.save(review);
        return review;
//        if (movieRepository.existsById(review.getIdMovie()))
//            movieRepository.findById(review.getIdMovie()).map(movie -> {
//                movie.setVoteAverage((movie.getVoteAverage() * movie.getVoteCount() + review.getStar()) / (movie.getVoteCount() + 1));
//                movie.setVoteCount(movie.getVoteCount() + 1);
//                return movieRepository.save(movie);
//            }).orElseThrow(() -> new IllegalStateException("No movie needs to update"));
    }

    public Review updateReview(String reviewId, Review updateReview) {
        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) {
            throw new IllegalStateException("review not found");
        }
        if (updateReview.getContent() == null && updateReview.getStars() == null) {
            throw new IllegalStateException("review does not change");
        }
        if(updateReview.getContent() != null) {
            review.setContent(updateReview.getContent());
        }
        if(updateReview.getStars() != null) {
            review.setStars(updateReview.getStars());
        }
        review.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        reviewRepository.save(review);
        return review;
//        movieRepository.findById(oldReview.getIdMovie()).map(movie -> {
//            movie.setVoteAverage((movie.getVoteAverage() * movie.getVoteCount() - oldReview.getStar() + newReview.getStar()) / movie.getVoteCount());
//            return movieRepository.save(movie);
//        }).orElseThrow(() -> new IllegalStateException("Update error!"));

//        reviewRepository.findById(id).map(review -> {
//            review.setContent(newReview.getContent());
//            review.setStars(newReview.getStars());
//            review.setUpdatedAt(LocalDate.now());
//            return reviewRepository.save(review);
//        }).orElseThrow(
//                () -> new IllegalStateException("Review not found on :: " + newReview.getId())
//        );
    }
    public void deleteReview(String reviewId, String userId) {
        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) {
            throw new IllegalStateException("Review doesn't exist");
        }
        if (!review.getAuthor().getId().equals(userId)) {
            throw new IllegalStateException("User may not have permission");
        }
        reviewRepository.deleteById(reviewId);
    }

    public void reactReviewByUserId(String reviewId, String userId, String action) {
        customReviewRepository.updateReactReviewByUserId(reviewId, userId, action);
    }

//    public Review dislikeReviewByUserId(String reviewId, String userId) {
//        Update update = new Update();
//        update.addToSet("dislike").each(userId);
//        Criteria criteria = Criteria.where("_id").is(reviewId);
//        mongoTemplate.updateFirst(Query.query(criteria), update, "reviews");
//        return reviewRepository.findReviewsById(reviewId);
//    }
//
//    public Review unlikeReviewByUserId(String reviewId, String userId) {
//        Update update = new Update();
//        update.pull("like", userId);
//        Criteria criteria = Criteria.where("_id").is(reviewId);
//        mongoTemplate.updateFirst(Query.query(criteria), update, "reviews");
//        return reviewRepository.findReviewsById(reviewId);
//    }
//
//    public Review unDislikeReviewByUserId(String reviewId, String userId) {
//        Update update = new Update();
//        update.pull("dislike", userId);
//        Criteria criteria = Criteria.where("_id").is(reviewId);
//        mongoTemplate.updateFirst(Query.query(criteria), update, "reviews");
//        return reviewRepository.findReviewsById(reviewId);
//    }
}
