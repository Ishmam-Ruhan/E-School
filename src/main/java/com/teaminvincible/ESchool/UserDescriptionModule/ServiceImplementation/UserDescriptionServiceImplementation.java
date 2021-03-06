package com.teaminvincible.ESchool.UserDescriptionModule.ServiceImplementation;

import com.teaminvincible.ESchool.Configurations.Master.CurrentUser;
import com.teaminvincible.ESchool.CourseModule.DTO.CourseResponse;
import com.teaminvincible.ESchool.CourseModule.Service.CourseService;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.ExceptionManagement.CustomException;
import com.teaminvincible.ESchool.MeetingModule.DTO.MeetingResponse;
import com.teaminvincible.ESchool.MeetingModule.Entity.Meeting;
import com.teaminvincible.ESchool.MeetingModule.Service.MeetingService;
import com.teaminvincible.ESchool.TaskModule.DTO.TaskResponse;
import com.teaminvincible.ESchool.TaskModule.Entity.Task;
import com.teaminvincible.ESchool.TaskModule.Service.TaskService;
import com.teaminvincible.ESchool.UserDescriptionModule.DTO.UserDescriptionResponse;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import com.teaminvincible.ESchool.UserDescriptionModule.Repository.UserDescriptionRepository;
import com.teaminvincible.ESchool.UserDescriptionModule.Service.UserDescriptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Primary
public class UserDescriptionServiceImplementation implements UserDescriptionService {

    @Autowired
    private UserDescriptionRepository userDescriptionRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private CurrentUser currentUser;

    public UserDescription findUserDescriptionByUserId(String userId) throws CustomException{
        UserDescription userDescription =  userDescriptionRepository.findByuserId(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No User Description Found With Id: "+userId));

        return userDescription;
    }

    @Override
    public UserDescription createUserDescription(UserDescription userDescription) throws CustomException {
        if(userDescription.getUserId() == null)
            throw new CustomException(HttpStatus.BAD_REQUEST,"No User Id Found!");

        UserDescription userDescription1;

        try{
            userDescription1 = userDescriptionRepository.save(userDescription);
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Can't process it right now! Please try again.");
        }
        return userDescription1;
    }

    @Override
    public UserDescription updateUserDescription(UserDescription userDescription) throws CustomException {

        UserDescription oldUserDescription = getUserDescription();

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
    public UserDescription getUserDescription() throws CustomException {
        return findUserDescriptionByUserId(currentUser.getCurrentUserId());
    }

    @Override
    public UserDescriptionResponse getMinimalUserDescription() throws CustomException {
        UserDescription userDescription = getUserDescription();

        UserDescriptionResponse userDescriptionResponse
                = new UserDescriptionResponse();

            BeanUtils.copyProperties(userDescription, userDescriptionResponse);

            userDescriptionResponse.setCourses(this.getCoursesOfAUser());
            userDescriptionResponse.setMeetings(this.getMeetingsOfUser());
            userDescriptionResponse.setTasks(this.getTasksOfUser());

        return userDescriptionResponse;
    }

    @Override
    public Role getRoleFromUserDescription() throws CustomException {
        return getUserDescription().getRole();
    }

    @Override
    public Set<CourseResponse> getCoursesOfAUser() throws CustomException {
        UserDescription userDescription = getUserDescription();

        if(userDescription.getRole().equals(Role.STUDENT))
            userDescription.getCourses()
                    .stream()
                    .map(course -> {
                        CourseResponse courseResponse
                                = new CourseResponse();
                        BeanUtils.copyProperties(course,courseResponse);
                        return courseResponse;
                    })
                    .collect(Collectors.toSet());

        return courseService.getAllCourseOfATeacher(currentUser.getCurrentUserId());
    }

    @Override
    public Set<TaskResponse> getTasksOfUser() throws CustomException {
        UserDescription userDescription = findUserDescriptionByUserId(currentUser.getCurrentUserId());

        if(userDescription.getRole().equals(Role.STUDENT))
            return userDescription.getTasks()
                    .stream()
                    .map(task -> {
                        TaskResponse taskResponse = new TaskResponse();
                        BeanUtils.copyProperties(task,taskResponse);
                        return taskResponse;
                    })
                    .collect(Collectors.toSet());

        return taskService.getTasksOfACourseOwner(userDescription.getUserId());
    }

    @Override
    public Set<MeetingResponse> getMeetingsOfUser() throws CustomException {
        UserDescription userDescription = findUserDescriptionByUserId(currentUser.getCurrentUserId());

        if(userDescription.getRole().equals(Role.STUDENT))
            return userDescription.getMeetings()
                    .stream()
                    .map(meeting -> {
                        MeetingResponse response = new MeetingResponse();
                        BeanUtils.copyProperties(meeting, response);
                        return response;
                    })
                    .collect(Collectors.toSet());

        return meetingService.getAllMeetingsOfACreator(userDescription.getUserId());
    }

    @Override
    public void saveMeetingToUsers(Set<UserDescription> students, Meeting meeting) throws CustomException{
        try{
            students.forEach(
                    student -> {
                        Set<Meeting> meetingSet = student.getMeetings();
                        meetingSet.add(meeting);
                        userDescriptionRepository.save(student);
                    }
            );
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Something went wrong! Please try again.");
        }
    }

    @Override
    public void saveTasksToUsers(Set<UserDescription> students, Task newTask) throws CustomException {
        try{
            students.forEach(
                    student -> {
                        Set<Task> taskSet = student.getTasks();
                        taskSet.add(newTask);
                        userDescriptionRepository.save(student);
                    }
            );
        }catch (Exception ex){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Something went wrong! Please try again.");
        }
    }


}
