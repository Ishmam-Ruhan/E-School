package com.teaminvincible.ESchool.UserDescriptionModule.ServiceImplementation;

import com.teaminvincible.ESchool.Configurations.Master.CurrentUser;
import com.teaminvincible.ESchool.CourseModule.Entity.Course;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.TaskModule.Entity.Task;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Repository.UserDescriptionRepository;
import com.teaminvincible.ESchool.UserDescriptionModule.Service.UserDescriptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Primary
public class UserDescriptionServiceImplementation implements UserDescriptionService {

    @Autowired
    private UserDescriptionRepository userDescriptionRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CurrentUser currentUser;

    public UserDescription findUserDescriptionByUserId(String userId) throws CustomException{
        return userDescriptionRepository.findByuserId(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No User Description Found With Id: "+userId));
    }

    @Override
    public UserDescription updateUserDescription(UserDescription userDescription) throws CustomException {

        UserDescription oldUserDescription = getUserDescription(currentUser.getCurrentUserId());

        UserDescription updatedUserDescription = new UserDescription();

        BeanUtils.copyProperties(userDescription, updatedUserDescription);

        updatedUserDescription.setUserId(oldUserDescription.getUserId());

        try{
            userDescriptionRepository.save(updatedUserDescription);
        }catch (Exception ex){
            throw  new CustomException(HttpStatus.BAD_REQUEST, "Can't Process the Request Right Now. Please Try Again");
        }

        return updatedUserDescription;
    }

    @Override
    public UserDescription getUserDescription(String userId) throws CustomException {
        return findUserDescriptionByUserId(userId);
    }

    @Override
    public Role getRoleFromUserDescription() throws CustomException {
        return getUserDescription(currentUser.getCurrentUserId()).getRole();
    }

    @Override
    public Set<Course> getCoursesOfAUser() throws CustomException {
        UserDescription userDescription = getUserDescription(currentUser.getCurrentUserId());

        if(userDescription.getRole().equals(Role.STUDENT))return userDescription.getCourses();

        return courseService.getAllCourseOfATeacher(currentUser.getCurrentUserId());
    }

    @Override
    public Set<Task> getTasksOfUser() throws CustomException {
        return getUserDescription(currentUser.getCurrentUserId()).getTasks();
    }

    @Override
    public Set<Meeting> getMeetingsOfUser() throws CustomException {
        return getUserDescription(currentUser.getCurrentUserId()).getMeetings();
    }
}
