package com.example.moviereview.service;

import com.example.moviereview.model.Favorite;
import com.example.moviereview.model.Movie;
import com.example.moviereview.repository.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;

    public List<Favorite> getFavoritesByUserId(String userId) {
        return favoriteRepository.findFavoritesByUserId(userId);
    }

    public Favorite addFavorite(Movie movie, String userId) {
        Favorite newFavorite = new Favorite(movie, userId);
        return favoriteRepository.save(newFavorite);
    }

    public void deleteFavorite(String movieId, String userId) {
//        boolean exists = favoriteRepository.existsById(movieId);
//        if (!exists) {
//            throw new IllegalStateException(
//                    "Review with id " + favoriteId + " doesn't exist"
//            );
//        }
//        else
        try {
            List<Favorite> favoriteList = favoriteRepository.findFavoritesByUserId(userId);
            Favorite deletedFavorite = favoriteList.stream().filter(f -> f.getMovie().getId().equals(movieId)).collect(Collectors.toList()).get(0);
            favoriteRepository.deleteById(deletedFavorite.getId());
        } catch (Exception e) {
            throw new IllegalStateException("favorite not found");
        }


//            favoriteRepository.deleteFavoriteByMovie_IdAndUserId(movieId, userId);
    }
}
