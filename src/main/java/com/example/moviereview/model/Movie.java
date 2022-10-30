package com.example.moviereview.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Movie {
    String id;
    String name;
    String overview;
    String poster;
    Double rating;
    String releaseDate;

}
