package com.teaminvincible.ESchool.UserModule.Repository;

import com.teaminvincible.ESchool.UserModule.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByemail(String email);
}
