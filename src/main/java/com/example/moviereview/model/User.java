package com.example.moviereview.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Document(collection = "users")
@Data @NoArgsConstructor
public class User implements UserDetails {

    @Id
    private String id;

    private String email;
    private String password;
    private String name;
    private String avatar;
    private String phone;
    private Gender gender;
    private LocalDate birthday;

    @ReadOnlyProperty
    @DocumentReference(lookup="{'author':?#{#self._id} }", lazy=true)
    List<Review> reviews;

    private Boolean locked = false;
    private Boolean enabled = false;

    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
    private LocalDateTime updatedAt = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    private enum Gender {
        male,
        female
    }
}




