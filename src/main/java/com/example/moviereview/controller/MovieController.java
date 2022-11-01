package com.example.moviereview.controller;

import com.example.moviereview.model.MovieConsume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class MovieController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String base_url = "https://api.themoviedb.org/3/";
    private static final String api_key = "5941024e8620246ad84260d2dfdac7ce";

    private String generateURL(String apiKey, String type) {
        return base_url + type + "?api_key=" + apiKey;
    }

    @GetMapping("movie/popular")
    public List<MovieConsume> getPopular(@RequestParam(required = false) String language, @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/popular"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        MovieConsume popularMovie = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        log.info(popularMovie.toString() + "");
        return Arrays.asList(popularMovie);
    }

    @GetMapping("movie/now_playing")
    public List<MovieConsume> getNowPlayingMovie(@RequestParam(required = false) String language, @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/now_playing"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        MovieConsume popularMovie = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(popularMovie);
    }

    @GetMapping("movie/top_rated")
    public List<MovieConsume> getTopRatedMovie(@RequestParam(required = false) String language, @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/top_rated"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        MovieConsume popularMovie = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(popularMovie);
    }

    @GetMapping("movie/upcoming")
    public List<MovieConsume> getUpcomingMovie(@RequestParam(required = false) String language, @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/upcoming"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        MovieConsume popularMovie = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(popularMovie);
    }

    /*@GetMapping("/latest")
    public List<MovieConsume> getLatestMovie(@RequestParam String language) {
        String url = generateURL(api_key, "movie/latest");
        MovieConsume popularMovie = restTemplate.getForObject(url + "&language=" + language, MovieConsume.class);
        return Arrays.asList(popularMovie);
    }*/

    @GetMapping("search/movie")
    public List<MovieConsume> searchMovie(@RequestParam(required = false) String language,
                                          @RequestParam(required = false) Integer page,
                                          @RequestParam String query,
                                          @RequestParam(required = false) String year,
                                          @RequestParam(required = false) Boolean include_adult) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "search/movie"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (query != null) {
            baseurl.append("&query=").append(query);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        if (year != null) {
            baseurl.append("&year=").append(year);
        }
        if (include_adult != null) {
            baseurl.append("&include_adult=").append(include_adult);
        }
        MovieConsume popularMovie = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(popularMovie);
    }

}
