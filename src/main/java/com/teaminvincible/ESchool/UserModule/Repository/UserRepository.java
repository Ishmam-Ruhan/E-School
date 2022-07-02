package com.teaminvincible.ESchool.UserModule.Repository;

import com.teaminvincible.ESchool.UserModule.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
