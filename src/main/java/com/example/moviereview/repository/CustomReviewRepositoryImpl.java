package com.example.moviereview.repository;

import com.example.moviereview.model.Review;
import com.example.moviereview.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateReactReviewByUserId(String reviewId, String userId, String action) {
        Review review = mongoTemplate.findById(reviewId, Review.class);
        if (review == null) {
            throw new IllegalStateException("review does not exist");
        }
        List<String> reactList;
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(reviewId));
        Update update = new Update();
        if (action.equals("like")) {
            reactList = review.getLike();
        } else if (action.equals("dislike")) {
            reactList = review.getDislike();
        } else {
            throw new IllegalStateException("React name invalid. React only includes 'like' and 'dislike'");
        }
        if (reactList.contains(userId)) {
            update.pull(action, userId);
        } else {
            update.addToSet(action, userId);
        }
        mongoTemplate.updateFirst(query, update, Review.class);
    }

    @Override
    public List<Review> findReviewsByAuthorId(String authorId) {
        Query query = new Query();
        UserInfo author = new UserInfo(authorId);
        return mongoTemplate.find(query, Review.class);
    }
}
