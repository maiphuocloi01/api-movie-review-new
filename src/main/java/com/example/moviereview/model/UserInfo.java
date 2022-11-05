package com.example.moviereview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserInfo {
    @Id
    private String id;
    private String email;
    private String name;
    private String avatar;
    private String phone;
    private String gender;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserInfo(String id) {
        this.id = id;
    }
}
