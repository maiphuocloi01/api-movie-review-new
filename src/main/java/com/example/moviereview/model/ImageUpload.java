package com.example.moviereview.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUpload {
    public MultipartFile imageFile;
    public String imageUrl;
}
