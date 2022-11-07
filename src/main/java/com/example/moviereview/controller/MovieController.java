package com.example.moviereview.controller;

import com.example.moviereview.model.movie.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public MovieConsume getPopular(@RequestParam(required = false) String language, @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/popular"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        return restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
    }

    @GetMapping("movie/now_playing")
    public MovieConsume getNowPlayingMovie(@RequestParam(required = false) String language, @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/now_playing"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        return restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
    }

    @GetMapping("movie/top_rated")
    public MovieConsume getTopRatedMovie(@RequestParam(required = false) String language, @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/top_rated"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        return restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
    }

    @GetMapping("movie/upcoming")
    public MovieConsume getUpcomingMovie(@RequestParam(required = false) String language, @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/upcoming"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        return restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
    }

    @GetMapping("keyword/{keyword_id}/movies")
    public MovieConsume getMovieByKeywordId(@PathVariable("keyword_id") String keywordId,
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
        return restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
    }

    @GetMapping("discover/movie")
    public MovieConsume discoverMovie(@RequestParam(required = false) String language,
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
        return restTemplate.getForObject(baseurl.toString(), MovieConsume.class);

    }

    @GetMapping("movie/{movie_id}/recommendations")
    public MovieConsume getMovieRecommendation(@PathVariable("movie_id") String movieId,
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
        return restTemplate.getForObject(baseurl.toString(), MovieConsume.class);

    }

    @GetMapping("collection/{collection_id}")
    public Collection getCollection(@PathVariable("collection_id") String collectionId, @RequestParam(required = false) String language) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "collection/" + collectionId));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        return restTemplate.getForObject(baseurl.toString(), Collection.class);

    }

    /*@GetMapping("/latest")
    public List<MovieConsume> getLatestMovie(@RequestParam String language) {
        String url = generateURL(api_key, "movie/latest");
        MovieConsume popularMovie = restTemplate.getForObject(url + "&language=" + language, MovieConsume.class);
        return Arrays.asList(popularMovie);
    }*/

    @GetMapping("search/movie")
    public MovieConsume searchMovie(@RequestParam(required = false) String language,
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
        return restTemplate.getForObject(baseurl.toString(), MovieConsume.class);

    }

    @GetMapping("search/keyword")
    public Keyword searchKeyword(@RequestParam(required = false) Integer page,
                                 @RequestParam String query) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "search/keyword"));
        if (query != null) {
            baseurl.append("&query=").append(query);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        return restTemplate.getForObject(baseurl.toString(), Keyword.class);

    }

    @GetMapping("movie/{movie_id}")
    public MovieDetail getMovieDetail(@PathVariable("movie_id") String movieId,
                                      @RequestParam(required = false) String language) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/" + movieId));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        return restTemplate.getForObject(baseurl.toString(), MovieDetail.class);

    }

    @GetMapping("movie/{movie_id}/credits")
    public Credit getCast(@PathVariable("movie_id") String movieId,
                          @RequestParam(required = false) String language) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/" + movieId + "/credits"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        return restTemplate.getForObject(baseurl.toString(), Credit.class);
    }

    @GetMapping("movie/{movie_id}/videos")
    public VideoConsume getVideo(@PathVariable("movie_id") String movieId,
                                 @RequestParam(required = false) String language) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/" + movieId + "/videos"));
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        return restTemplate.getForObject(baseurl.toString(), VideoConsume.class);

    }

    @GetMapping("movie/{movie_id}/external_ids")
    public Social getSocial(@PathVariable("movie_id") String movieId) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "movie/" + movieId + "/external_ids"));
        return restTemplate.getForObject(baseurl.toString(), Social.class);

    }

    @GetMapping("credit/{credit_id}")
    public CreditConsume getCreditDetail(@PathVariable("credit_id") String credit_id) {
        StringBuilder baseurl = new StringBuilder();
        baseurl.append(generateURL(api_key, "credit/" + credit_id));
        return restTemplate.getForObject(baseurl.toString(), CreditConsume.class);
    }

    @GetMapping("/movie/{movie_id}/images")
    public ImageConsume getImages(@PathVariable("movie_id") String movie_id) {
        return restTemplate.getForObject(generateURL(api_key, "movie/" + movie_id + "/images"), ImageConsume.class);
    }

    @GetMapping("trending/movie/{time_window}")
    public MovieConsume getTrendingMovie(@PathVariable("time_window") String time_window,
                                         @RequestParam(required = false) String language,
                                         @RequestParam(required = false) String page) {
        StringBuilder baseurl = new StringBuilder();
        if (language != null) {
            baseurl.append("&language=").append(language);
        }
        if (page != null) {
            baseurl.append("&page=").append(page);
        }
        baseurl.append(generateURL(api_key, "trending/movie/" + time_window));
        return restTemplate.getForObject(baseurl.toString(), MovieConsume.class);
    }



}
