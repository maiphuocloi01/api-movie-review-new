package com.example.moviereview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Social{
    public int id;
    public String imdb_id;
    public String facebook_id;
    public String instagram_id;
    public String twitter_id;
}
