package com.saurabh.forgotpassword.repositries;

import com.saurabh.forgotpassword.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByToken(String token);

}
