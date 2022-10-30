package com.example.moviereview.controller;
import com.example.moviereview.model.Favorite;
import com.example.moviereview.model.Movie;
import com.example.moviereview.model.Review;
import com.example.moviereview.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    public List<Favorite> getFavoritesByUserId() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        return favoriteService.getFavoritesByUserId(userId);
    }

    @PostMapping
    public Favorite addToFavorite(@RequestBody Movie movie) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        return favoriteService.addFavorite(movie, userId);
    }

    @DeleteMapping(path = "/{movieId}")
    public ResponseEntity<?> deleteFromFavorite(@PathVariable("movieId") String movieId) {
        try {
            String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            favoriteService.deleteFavorite(movieId, userId);
            return ResponseEntity.status(200).build();
        } catch (IllegalStateException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
    }
}
