package com.teaminvincible.ESchool.CourseModule.Repository;

import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {
    Course findBycourseJoiningCode(String joiningCode);
}
