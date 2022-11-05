package com.example.moviereview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "reviews")
@Data @NoArgsConstructor
public class Review {
    @Id
    private String id;
    private String content;
    private Movie movie;
    @DocumentReference(collection = "users")
    private UserInfo author;
    private Double stars;
    private List<String> like = new ArrayList<>();
    private List<String> dislike = new ArrayList<>();
    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    private LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
//    private ZonedDateTime createdAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Ho_Chi_Minh"));
//    private ZonedDateTime updatedAt = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Ho_Chi_Minh"));

    public Review(String content, Movie movie, UserInfo author, Double stars) {
        this.content = content;
        this.movie = movie;
        this.author = author;
        this.stars = stars;
    }

    public int countLikes() {
        return like.size();
    }

    public int countDislikes() {
        return dislike.size();
    }

}