package com.teaminvincible.ESchool.UserDescriptionModule.Service;

import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.TaskModule.Entity.Task;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UserDescriptionService {

    UserDescription updateUserDescription(UserDescription userDescription) throws CustomException;

    UserDescription getUserDescription(String userId) throws CustomException;

    Role getRoleFromUserDescription(String userId) throws CustomException;

    // Need to check user is student or teacher. If teacher then retrive all courses from course_Owner row and add them to dto
    Set<Course> getCoursesOfAUser(String userId) throws CustomException;

    Set<Task> getTasksOfUser(String userId) throws CustomException;

    Set<Meeting> getMeetingsOfUser(String userId) throws CustomException;

}
