package com.teaminvincible.ESchool.MeetingModule.Repository;

import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, String>, JpaSpecificationExecutor<Meeting>{

}
