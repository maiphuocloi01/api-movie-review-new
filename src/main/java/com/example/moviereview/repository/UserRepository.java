package com.example.moviereview.repository;

import com.example.moviereview.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findUserByEmail(String email);
    @Query(fields = "{ 'password':  0 }")
    User findUserById(String id);


}
