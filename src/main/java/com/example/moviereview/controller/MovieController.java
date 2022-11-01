package com.example.moviereview.controller;

import com.example.moviereview.model.MovieConsume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/api/v1/movie")
public class MovieController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "https://api.themoviedb.org/3/movie/popular?api_key=5941024e8620246ad84260d2dfdac7ce";
    private static final String api_key = "5941024e8620246ad84260d2dfdac7ce";

    @GetMapping("/popular")
    public List<MovieConsume> getPopular(){
        MovieConsume popularMovie = restTemplate.getForObject(url, MovieConsume.class);
        log.info(popularMovie.toString() + "");
        return Arrays.asList(popularMovie);
    }
}
