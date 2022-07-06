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

    UserDescription createUserDescription(UserDescription userDescription) throws CustomException;
    UserDescription updateUserDescription(UserDescription userDescription) throws CustomException;

    UserDescription getUserDescription(String userId) throws CustomException;

    Role getRoleFromUserDescription() throws CustomException;

    Set<Course> getCoursesOfAUser() throws CustomException;

    Set<Task> getTasksOfUser() throws CustomException;

    Set<Meeting> getMeetingsOfUser() throws CustomException;

}
