package com.saurabh.forgotpassword.service;


import com.saurabh.forgotpassword.entities.User;
import com.saurabh.forgotpassword.repositries.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    // Token expiration time in minutes
    private static final long TOKEN_EXPIRATION_MINUTES = 30;

    @Autowired
    private UserRepo userRepo;

    public String forgotPass(String email){
        Optional<User> userOptional = Optional.ofNullable(userRepo.findByEmail(email)) ;
        if (userOptional.isEmpty()){
            return "Invalid email id";
        }
        User user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());

        user = userRepo.save(user);
        return user.getToken();


    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }



    public String resetPass(String token, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepo.findByToken(token));
        if(userOptional.isEmpty()){
            return "Invalid token";
        }
        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();
        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }
        User user = userOptional.get();
        user.setPassword(password);
        user.setToken(null);
        user.setTokenCreationDate(null);
        userRepo.save(user);
        return "Your password successfully updated.";

    }

    private boolean isTokenExpired(LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >=TOKEN_EXPIRATION_MINUTES;
    }
}
