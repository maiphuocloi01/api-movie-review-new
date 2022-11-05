package com.example.moviereview.repository;

import com.example.moviereview.model.Review;
import com.example.moviereview.model.User;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final MongoTemplate mongoTemplate;

    public CustomUserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void updateInfoUserById(String userId, HashMap<String, String> updateFields) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userId));
        Update update = new Update();
        for(Map.Entry<String, String> field: updateFields.entrySet()) {
            update.set(field.getKey(), field.getValue());
        }
        update.set("updatedAt", LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        mongoTemplate.updateFirst(query, update, User.class);
    }
}
