package com.teaminvincible.ESchool.UserDescriptionModule.Repository;

import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDescriptionRepository extends
        JpaRepository<UserDescription,String>, JpaSpecificationExecutor<UserDescription> {
    Optional<UserDescription> findByuserId(String id);
}
