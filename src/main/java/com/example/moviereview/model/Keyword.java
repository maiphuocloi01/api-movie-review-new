package com.example.moviereview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Keyword{
    public int page;
    public ArrayList<KeywordDetail> results;
    public int total_pages;
    public int total_results;
}
