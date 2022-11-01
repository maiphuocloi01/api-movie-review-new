package com.example.moviereview.controller;

import com.example.moviereview.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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
        MovieConsume objConsume = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(objConsume);
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
        MovieConsume objConsume = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(objConsume);
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
        MovieConsume objConsume = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(objConsume);
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
        MovieConsume objConsume = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(objConsume);
    }

    @GetMapping("keyword/{keyword_id}/movies")
    public List<MovieConsume> getMovieByKeywordId(@PathVariable("keyword_id") String keywordId,
                                                  @RequestParam(required = false) String language,
                                                  @RequestParam(required = false) Boolean include_adult) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "keyword/" + keywordId + "/movies"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (include_adult != null) {
            baseurl.append("&include_adult=").append(include_adult);
        }
        MovieConsume objConsume = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(objConsume);
    }

    @GetMapping("discover/movie")
    public List<MovieConsume> discoverMovie(@RequestParam(required = false) String language,
                                            @RequestParam(required = false) Boolean include_adult,
                                            @RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) String sort_by,
                                            @RequestParam(required = false) String with_genres) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "discover/movie"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (include_adult != null) {
            baseurl.append("&include_adult=").append(include_adult);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        if (sort_by != null) {
            baseurl.append("&sort_by=").append(sort_by);
        }
        if (with_genres != null) {
            baseurl.append("&with_genres=").append(with_genres);
        }
        MovieConsume objConsume = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(objConsume);
    }

    @GetMapping("movie/{movie_id}/recommendations")
    public List<MovieConsume> getMovieRecommendation(@PathVariable("movie_id") String movieId,
                                                     @RequestParam(required = false) String language,
                                                     @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/" + movieId + "/recommendations"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        MovieConsume objConsume = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(objConsume);
    }

    @GetMapping("collection/{collection_id}")
    public List<Collection> getCollection(@PathVariable("collection_id") String collectionId, @RequestParam(required = false) String language) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "collection/" + collectionId));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        Collection objConsume = restTemplate.getForObject(baseurl.toString(), Collection.class);
        return Arrays.asList(objConsume);
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
        MovieConsume objConsume = restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
        return Arrays.asList(objConsume);
    }

    @GetMapping("search/keyword")
    public List<Keyword> searchKeyword(@RequestParam(required = false) Integer page,
                                       @RequestParam String query) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "search/keyword"));
        if (query != null) {
            baseurl.append("&query=").append(query);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        Keyword objConsume = restTemplate.getForObject(baseurl.toString(), Keyword.class);
        return Arrays.asList(objConsume);
    }

    @GetMapping("movie/{movie_id}")
    public List<MovieDetail> getMovieDetail(@PathVariable("movie_id") String movieId,
                                            @RequestParam(required = false) String language) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/" + movieId));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        MovieDetail objConsume = restTemplate.getForObject(baseurl.toString(), MovieDetail.class);
        return Arrays.asList(objConsume);
    }

    @GetMapping("movie/{movie_id}/credits")
    public List<Credit> getCast(@PathVariable("movie_id") String movieId,
                                @RequestParam(required = false) String language) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/" + movieId + "/credits"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        Credit objConsume = restTemplate.getForObject(baseurl.toString(), Credit.class);
        return Arrays.asList(objConsume);
    }

    @GetMapping("movie/{movie_id}/videos")
    public List<VideoConsume> getVideo(@PathVariable("movie_id") String movieId,
                                       @RequestParam(required = false) String language) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/" + movieId + "/videos"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        VideoConsume objConsume = restTemplate.getForObject(baseurl.toString(), VideoConsume.class);
        return Arrays.asList(objConsume);
    }

    @GetMapping("movie/{movie_id}/external_ids")
    public List<Social> getSocial(@PathVariable("movie_id") String movieId) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/" + movieId + "/external_ids"));
        Social objConsume = restTemplate.getForObject(baseurl.toString(), Social.class);
        return Arrays.asList(objConsume);
    }

}
