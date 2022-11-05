package com.example.moviereview.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoConsume{
    public int id;
    public ArrayList<Video> results;
}
