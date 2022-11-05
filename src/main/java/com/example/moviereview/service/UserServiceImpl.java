package com.example.moviereview.service;

import com.example.moviereview.model.Review;
import com.example.moviereview.model.User;
import com.example.moviereview.repository.CustomUserRepository;
import com.example.moviereview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    
    private final UserRepository userRepository;
    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("email not found");
        }
        return user;
    }
    @Override
    public User signUpUser(User user) {
        User userExist = userRepository.findUserByEmail(user.getEmail());
        if (userExist != null) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public void enableUserByEmail(String email) {
        User userExist = userRepository.findUserByEmail(email);
        if (userExist != null) {
            userExist.setEnabled(true);
            userExist.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            userRepository.save(userExist);
        } else {
            throw new UsernameNotFoundException("email not found");
        }
    }

    @Override
    public void changePasswordByEmail(String email, String password) {
        User userExist = userRepository.findUserByEmail(email);
        if (userExist != null) {
            String encodedPassword = passwordEncoder.encode(password);
            userExist.setPassword(encodedPassword);
            userExist.setUpdatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
            userRepository.save(userExist);
        } else {
            throw new UsernameNotFoundException("email not found");
        }
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("email not found");
        }
        user.setPassword("******");
        return user;
    }

    @Override
    public User getUserById(String userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("user with " + userId + " not found");
        }
        return user;
    }

    public HashMap<String, Double> getStatisticsByUserId(String userId) {
        HashMap<String, Double> statistics = new HashMap<>();
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("user with " + userId + " not found");
        }
        List<Review> reviewList = user.getReviews();
        int numReviews = reviewList.size();
        statistics.put("reviews", (double) numReviews);

        int numLikes = reviewList.stream().mapToInt(Review::countLikes).sum();
        statistics.put("likes", (double) numLikes);

        int numDislikes = reviewList.stream().mapToInt(Review::countDislikes).sum();
        statistics.put("dislikes", (double) numDislikes);

        double totalRatings = reviewList.stream().mapToDouble(Review::getStars).sum();
        double avgRatings = totalRatings/numReviews;
        statistics.put("avgStars", (double) Math.round(avgRatings*100) / 100);

        return statistics;
    }

    @Override
    public void updateUserById(String userId, HashMap<String, String> updateFields) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("user with " + userId + " not found");
        }
        customUserRepository.updateInfoUserById(userId, updateFields);
    }

    @Override
    public void updateImageById(String userId, HashMap<String, String> updateFields) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("user with " + userId + " not found");
        }
        customUserRepository.updateInfoUserById(userId, updateFields);
    }

    @Override
    public void changePasswordById(String userId, String oldPassword, String newPassword) {
        Optional<User> existUser = userRepository.findById(userId);
        if (existUser.isPresent()) {
            User user = existUser.get();
            if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new IllegalStateException("old password incorrect");
            }
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedNewPassword);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("user with " + userId + " not found");
        }

    }
}
