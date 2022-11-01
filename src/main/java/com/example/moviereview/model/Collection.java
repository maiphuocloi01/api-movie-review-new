package com.example.moviereview.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection{
    public int id;
    public String name;
    public String overview;
    public String poster_path;
    public String backdrop_path;
    public ArrayList<Part> parts;
}
