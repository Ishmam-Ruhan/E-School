package com.teaminvincible.ESchool.CourseModule.Service;

import com.teaminvincible.ESchool.CourseModule.DTO.CourseSearchCriteria;
import com.teaminvincible.ESchool.CourseModule.DTO.CreateCourseRequest;
import com.teaminvincible.ESchool.CourseModule.DTO.UpdateCourseRequest;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.TaskModule.DTO.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface CourseService {

    Course createCourse(CreateCourseRequest createCourseRequest) throws CustomException;

    Course joinCourse(String joiningCode) throws CustomException;

    String unEnrollFromACourse(String courseId) throws CustomException;

    Course updateCourse(UpdateCourseRequest updateCourseRequest) throws CustomException;

    String deleteCourse(String courseId) throws CustomException;

    Set<Course> getAllCourseOfATeacher(String userId) throws CustomException;
    Set<Meeting> getAllMeetingsOfCourse(String courseId) throws CustomException;

    Set<TaskResponse> getAllTasksOfACourse(String courseId) throws CustomException;
    Set<Course> findCourse(CourseSearchCriteria searchCriteria) throws CustomException;
}
