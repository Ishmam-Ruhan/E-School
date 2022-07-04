package com.teaminvincible.ESchool.CourseModule.Service;

import com.teaminvincible.ESchool.CourseModule.DTO.CourseSearchCriteria;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface CourseService {

    Course createCourse(String userId, Course course) throws CustomException;

    Course updateCourse(String userId, Course course) throws CustomException;

    String deleteCourse(String userId, String courseId) throws CustomException;

    Set<Course> getAllCourseOfAUser(String userId) throws CustomException;

    Set<Course> findCourse(String userId, CourseSearchCriteria searchCriteria) throws CustomException;
}
