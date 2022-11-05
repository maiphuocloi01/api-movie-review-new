package com.example.moviereview.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("confirmation_tokens")
@Getter @Setter @NoArgsConstructor
public class Otp {
    @Id
    private String id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    private String email;

    public Otp(String email, String token, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.email = email;
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
