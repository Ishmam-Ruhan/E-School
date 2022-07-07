package com.teaminvincible.ESchool.CourseModule.Service;

import com.teaminvincible.ESchool.CourseModule.DTO.*;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingResponse;
import com.teaminvincible.ESchool.TaskModule.DTO.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface CourseService {

    CourseResponse createCourse(CreateCourseRequest createCourseRequest) throws CustomException;

    CourseResponse joinCourse(String joiningCode) throws CustomException;

    String unEnrollFromACourse(String courseId) throws CustomException;

    CourseResponse updateCourse(UpdateCourseRequest updateCourseRequest) throws CustomException;

    String deleteCourse(String courseId) throws CustomException;

    Set<CourseResponse> getAllCourseOfATeacher(String userId) throws CustomException;

    Set<MeetingResponse> getAllMeetingsOfCourse(String courseId) throws CustomException;

    Set<TaskResponse> getAllTasksOfACourse(String courseId) throws CustomException;

    Set<Course> findCourse(CourseSearchCriteria searchCriteria) throws CustomException;

    Course getCourseDetails(String courseId);
}
