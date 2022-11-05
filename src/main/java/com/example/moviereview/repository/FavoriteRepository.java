package com.example.moviereview.repository;

import com.example.moviereview.model.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends MongoRepository<Favorite, String> {
    List<Favorite> findFavoritesByUserId(String userId);

    @Query(value = "{ 'movie.$id': ?0, 'userId': ?1}")
    void deleteFavoriteByMovie_IdAndUserId(String movieId, String userId);

}
