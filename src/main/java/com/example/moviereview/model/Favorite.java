package com.example.moviereview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Document(collection = "favorites")
@Data
@NoArgsConstructor
public class Favorite {
    @Id
    private String id;
    private Movie movie;
    private String userId;
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

    public Favorite(Movie movie, String userId) {
        this.movie = movie;
        this.userId = userId;
    }
}
